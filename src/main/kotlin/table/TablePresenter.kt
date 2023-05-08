package table

interface TablePresenter<T> {

    operator fun get(key: Any): T

    fun getOrNull(key: Any): T?

    fun getAll(): Collection<T>

}

interface MutableTablePresenter<E> : TablePresenter<E> {

    operator fun set(key: Any, newValue: E)

}

interface InheritableTablePresenter<T> : TablePresenter<T> {

    fun getFromChildren(key: Any): T?

    fun getFromOnlyNode(key: Any): T?

    fun addChild(child: InheritableTablePresenter<T>)

    fun getChildren(): List<InheritableTablePresenter<T>>

}
