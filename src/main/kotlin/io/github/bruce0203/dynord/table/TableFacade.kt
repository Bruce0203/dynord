package io.github.bruce0203.dynord.table

interface TableFacade<T : Table<T2>, T2 : Any> { val table: T }

interface CompositeTableFacade<T : CompositeTable<T2>, T2 : Any> : TableFacade<T, T2>

open class MutableTableFacade<T : Any, V : Any, T2 : MutableTable<T>>(
    override val table: T2,
    protected open val wrapper: (T) -> V,
    private val getter: (V) -> T
)  : MutableTable<V>, TableFacade<T2, T> {
    companion object { private const val serialVersionUID = -1234985791872903824L }

    override fun get(key: Any): V = table[key].run(wrapper)

    override fun getOrNull(key: Any): V? = table.getOrNull(key)?.run(wrapper)

    override fun getAll(): Collection<V> = table.getAll().map { it.run(wrapper) }

    override fun set(key: Any, newValue: V) { table[key] = newValue.run(getter) }

    override fun remove(key: Any): V? = table.remove(key)?.run(wrapper)
}

open class NodeTableFacade<T : Any, V : Any, T2 : CompositeTable<T>>(
    override val table: T2,
    override val wrapper: (T) -> V,
    private val getter: (V) -> T,
) : MutableTableFacade<T, V, T2>(table, wrapper, getter), CompositeTable<V>, CompositeTableFacade<T2, T> {
    companion object { private const val serialVersionUID = 1234985791872903824L }

    override fun getFromChildren(key: Any): V? = table.getFromChildren(key)?.run(wrapper)

    override fun getFromOnlyNode(key: Any): V? = table.getFromOnlyNode(key)?.run(wrapper)

    override fun getFromSkippedNode(key: Any, depth: Int): V? = table.getFromSkippedNode(key, depth)?.run(wrapper)

    override fun setToSkippedNode(key: Any, value: V, depth: Int) = table.setToSkippedNode(key, value.run(getter), depth)

    override fun getChildren(): List<CompositeTable<V>> = table.getChildren()
        .map { NodeTableFacade(it, wrapper, getter) }

    @Suppress("UNCHECKED_CAST")
    override fun addChild(child: CompositeTable<V>) {
        if (child is CompositeTableFacade<*, *>) {
            table.addChild(child.table as CompositeTable<T>)
        }
    }
}