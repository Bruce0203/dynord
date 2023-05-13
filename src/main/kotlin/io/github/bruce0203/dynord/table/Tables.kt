package io.github.bruce0203.dynord.table

import io.github.bruce0203.dynord.util.FastArrayList
import io.github.bruce0203.dynord.util.FastElementNotFoundException
import io.github.bruce0203.dynord.util.FastHashMap
import io.github.bruce0203.dynord.util.fastFirstOrNull

open class SafeTable<E : Any>(val defaultGet: () -> E? = { null }) : MutableTable<E> {
    companion object { private const val serialVersionUID = -7390903873381167900L }

    protected val elements: FastHashMap<Any, E> = FastHashMap()

    override operator fun set(key: Any, newValue: E) { elements[key] = newValue }

    override fun get(key: Any): E =
        getOrNull(key)?: defaultGet()?.apply { set(key, this) } ?: throw FastElementNotFoundException

    override fun getOrNull(key: Any): E? = elements[key]
    override fun getAll(): Collection<E> = elements.values
}

open class NodeTable<E : Any>(defaultGet: () -> E? = { null }) : SafeTable<E>(defaultGet), CompositeTable<E> {
    companion object { private const val serialVersionUID = -1390903873381167909L }

    private val children: FastArrayList<CompositeTable<E>> = FastArrayList()

    override fun getFromOnlyNode(key: Any): E? = elements[key]

    override fun getChildren(): List<CompositeTable<E>> = children

    override fun addChild(child: CompositeTable<E>) { children.add(child) }

    override fun getOrNull(key: Any): E? = getFromOnlyNode(key) ?: getFromChildren(key)

    override fun getFromChildren(key: Any): E? = children.run {
        fastFirstOrNull { child -> child.getFromOnlyNode(key) } ?: fastFirstOrNull { child -> child.getFromChildren(key) }
    }
}
