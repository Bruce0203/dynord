package table

typealias CollectionTable = NodeTable<RowTable>
typealias RowTable = NodeTable<Any>

open class TableFacade<T : Table<*>, V>(
    protected open val table: Table<T>,
    private val wrapper: (T) -> V,
)  : Table<V> {
    override fun get(key: Any): V = table[key].run(wrapper)

    override fun getOrNull(key: Any): V? = table.getOrNull(key)?.run(wrapper)

    override fun getAll(): Collection<V> = table.getAll().map { it.run(wrapper) }
}

open class MutableTableFacade<T : MutableTable<*>, V>(
    override val table: MutableTable<T>,
    wrapper: (T) -> V,
    private val getter: (V) -> T
) : TableFacade<T, V>(table, wrapper), MutableTable<V> {
    override fun set(key: Any, newValue: V) { table[key] = newValue.run(getter) }
}