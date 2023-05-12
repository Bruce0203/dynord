package table

interface Table<T> {

    operator fun get(key: Any): T

    fun getOrNull(key: Any): T?

    fun getAll(): Collection<T>

}

interface MutableTable<E> : Table<E> {

    operator fun set(key: Any, newValue: E)

}

interface InheritableTable<T> : Table<T> {

    fun getFromChildren(key: Any): T?

    fun getFromOnlyNode(key: Any): T?

    fun addChild(child: InheritableTable<T>)

    fun getChildren(): List<InheritableTable<T>>

}
