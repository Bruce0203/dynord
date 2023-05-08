package table

fun <T : TablePresenter<*>, V : TableVisitor<T>> tableFacade(table: MutableTablePresenter<T>, newValue: (T) -> V): MutableTablePresenter<V> {
    return object : MutableTablePresenter<V> {
        override fun get(key: Any): V = table[key].run(newValue)

        override fun getOrNull(key: Any): V? = table.getOrNull(key)?.run(newValue)

        override fun getAll(): Collection<V> = table.getAll().map { it.run(newValue) }

        override fun set(key: Any, newValue: V) { table[key] = newValue.table }
    }
}

open class TableFacade<T, V>(
    private val table: MutableTablePresenter<T>,
    private val newValue: (T) -> V
) : TablePresenter<V> {

    companion object fun invoke(table: MutableTablePresenter<T>, newValue: (T) -> V) {

    }

    override fun get(key: Any): V = table[key].run(newValue)

    override fun getOrNull(key: Any): V? = table.getOrNull(key)?.run(newValue)

    override fun getAll(): Collection<V> = table.getAll().map { it.run(newValue) }

}