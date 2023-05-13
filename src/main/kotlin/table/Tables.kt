package table

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap
import util.*
import util.newSafeList
import util.newSafeMap
import java.util.concurrent.CopyOnWriteArrayList

open class SafeTable<E : Any>(val defaultGet: () -> E? = { null }) : MutableTable<E> {
    companion object { private const val serialVersionUID = -7390903873381167900L }

    protected val elements: ConcurrentLinkedHashMap<Any, E> = newSafeMap()

    override operator fun set(key: Any, newValue: E) { elements[key] = newValue }

    override fun get(key: Any): E =
        getOrNull(key)?: defaultGet()?.apply { set(key, this) } ?: throw FastElementNotFoundException

    override fun getOrNull(key: Any): E? = elements[key]
    override fun getAll(): Collection<E> = elements.values
}

open class NodeTable<E : Any>(defaultGet: () -> E? = { null }) : SafeTable<E>(defaultGet), InheritableTable<E> {
    companion object { private const val serialVersionUID = -1390903873381167909L }

    private val children: CopyOnWriteArrayList<InheritableTable<E>> = newSafeList()

    override fun getFromOnlyNode(key: Any): E? = elements[key]

    override fun getChildren(): List<InheritableTable<E>> = children

    override fun addChild(child: InheritableTable<E>) { children.add(child) }

    override fun getOrNull(key: Any): E? = getFromOnlyNode(key) ?: getFromChildren(key)

    override fun getFromChildren(key: Any): E? = children.run {
        fastFirstOrNull { child -> child.getFromOnlyNode(key) } ?: fastFirstOrNull { child -> child.getFromChildren(key) }
    }
}
