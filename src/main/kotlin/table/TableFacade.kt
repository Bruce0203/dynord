package table

infix fun <T : MutableTablePresenter<*>, V : TableVisitor<T>> MutableTablePresenter<T>.facade(newValue: (T) -> V)
= TableFacade(this, newValue)

open class TableFacade<T : MutableTablePresenter<*>, V : TableVisitor<T>>(
    private val table: MutableTablePresenter<T>,
    private val newValue: (T) -> V
)  : MutableTablePresenter<V> {

    override fun get(key: Any): V = table[key].run(newValue)

    override fun getOrNull(key: Any): V? = table.getOrNull(key)?.run(newValue)

    override fun getAll(): Collection<V> = table.getAll().map { it.run(newValue) }

    override fun set(key: Any, newValue: V) { table[key] = newValue.table }
}