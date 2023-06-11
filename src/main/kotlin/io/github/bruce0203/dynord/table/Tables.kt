package io.github.bruce0203.dynord.table

import io.github.bruce0203.dynord.util.FastArrayList
import io.github.bruce0203.dynord.util.FastElementNotFoundException
import io.github.bruce0203.dynord.util.FastHashMap
import io.github.bruce0203.dynord.util.fastFirstOrNull

open class SafeTable<E : Any>(val defaultGet: () -> E? = { null }) : MutableTable<E> {
    protected val elements: FastHashMap<Any, E> = FastHashMap()

    override operator fun set(key: Any, newValue: E) { elements[key] = newValue }

    override fun remove(key: Any): E? = elements.remove(key)

    override fun get(key: Any): E =
        getOrNull(key)?: defaultGet()?.apply { set(key, this) } ?: throw FastElementNotFoundException

    override fun getOrNull(key: Any): E? = elements[key]
    override fun getAll(): Collection<E> = elements.values
}

open class NodeTable<E : Any>(defaultGet: () -> E? = { null }) : SafeTable<E>(defaultGet), CompositeTable<E> {
    private val children: FastArrayList<CompositeTable<E>> = FastArrayList()

    override fun getFromOnlyNode(key: Any): E? = elements[key]

    override fun getChildren(): List<CompositeTable<E>> = children

    override fun addChild(child: CompositeTable<E>) {
        if (!children.contains(child)) children.add(child)
    }

    override fun getOrNull(key: Any): E? = getFromOnlyNode(key) ?: getFromChildren(key)

    override fun getFromChildren(key: Any): E? = children.run {
        fastFirstOrNull { child -> child.getFromOnlyNode(key) }
            ?: fastFirstOrNull { child -> child.getFromChildren(key) }
    }

    private fun getSkippedNode(depth: Int): CompositeTable<E> {
        var v: CompositeTable<E> = this
        for (i in 0 until depth) { v = v.getChildren()[0] }
        return v
    }

    override fun getFromSkippedNode(key: Any, depth: Int): E? = getSkippedNode(depth).getOrNull(key)

    override fun setToSkippedNode(key: Any, value: E, depth: Int) { getSkippedNode(depth)[key] = value }
}
