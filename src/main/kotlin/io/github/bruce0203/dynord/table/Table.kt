package io.github.bruce0203.dynord.table

import java.io.Serializable

interface Table<T : Any> : Serializable {
    operator fun get(key: Any): T

    fun getOrNull(key: Any): T?

    fun getAll(): Collection<T>
}

interface MutableTable<E : Any> : Table<E> {
    operator fun set(key: Any, newValue: E)
}

interface CompositeTable<T : Any> : Table<T> {
    fun getFromChildren(key: Any): T?

    fun getFromOnlyNode(key: Any): T?

    fun addChild(child: CompositeTable<T>)

    fun getChildren(): List<CompositeTable<T>>

    fun getFromSkippedNode(key: Any, depth: Int): T?
}
