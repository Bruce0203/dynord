package table

import java.io.Serializable

interface Table<T : Any> : Serializable {

    operator fun get(key: Any): T

    fun getOrNull(key: Any): T?

    fun getAll(): Collection<T>

}

interface MutableTable<E : Any> : Table<E> {

    operator fun set(key: Any, newValue: E)

}

interface InheritableTable<T : Any> : Table<T> {

    fun getFromChildren(key: Any): T?

    fun getFromOnlyNode(key: Any): T?

    fun addChild(child: InheritableTable<T>)

    fun getChildren(): List<InheritableTable<T>>

}
