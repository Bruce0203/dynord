package table

open class TableFacade<T : Table<*>, V : Any>(
    protected open val table: Table<T>,
    private val wrapper: (T) -> V,
)  : Table<V> {
    companion object { private const val serialVersionUID = -7390903879381167909L }

    override fun get(key: Any): V = table[key].run(wrapper)

    override fun getOrNull(key: Any): V? = table.getOrNull(key)?.run(wrapper)

    override fun getAll(): Collection<V> = table.getAll().map { it.run(wrapper) }
}

open class MutableTableFacade<T : MutableTable<*>, V : Any>(
    override val table: MutableTable<T>,
    wrapper: (T) -> V,
    private val getter: (V) -> T
) : TableFacade<T, V>(table, wrapper), MutableTable<V> {
    companion object { private const val serialVersionUID = -7390903873381167909L }

    override fun set(key: Any, newValue: V) { table[key] = newValue.run(getter) }
}