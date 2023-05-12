package table

typealias CollectionTable = NodeTable<RowTable>
typealias RowTable = NodeTable<Any>

open class TableFacade<T : MutableTable<*>, V : TableVisitor<T>>(
    private val table: MutableTable<T>,
    private val newValue: (T) -> V
)  : MutableTable<V> {

    override fun get(key: Any): V = table[key].run(newValue)

    override fun getOrNull(key: Any): V? = table.getOrNull(key)?.run(newValue)

    override fun getAll(): Collection<V> = table.getAll().map { it.run(newValue) }

    override fun set(key: Any, newValue: V) { table[key] = newValue.table }
}