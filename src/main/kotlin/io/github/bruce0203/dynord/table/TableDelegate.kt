package io.github.bruce0203.dynord.table

import io.github.bruce0203.dynord.util.FastElementNotFoundException
import kotlin.reflect.KProperty

interface TableDelegate<T : Any> {
    fun getTableValue(key: Any): T

    fun setTableValue(key: Any, value: T)
}

inline operator fun <T : Any, reified T2> TableDelegate<T>.setValue(thisRef: T2, property: KProperty<*>, value: T) = setTableValue(property.hashCode(), value)
inline operator fun <T : Any, reified T2> TableDelegate<T>.getValue(thisRef: T2, property: KProperty<*>): T = getTableValue(property.hashCode())

class MutableTableDelegate<T : Any, V : T>(
    private val table: MutableTable<T>
) : TableDelegate<V>, MutableTable<T> by table {
    companion object { private const val serialVersionUID = -1390902875381465903L }

    @Suppress("UNCHECKED_CAST")
    override fun getTableValue(key: Any): V = table[key] as V

    override fun setTableValue(key: Any, value: V) { table[key] = value }
}

class LazyTableDelegate<T : Any, V : T>(
    private val table: MutableTable<T>,
    private val code: () -> V
) : MutableTable<T> by table, TableDelegate<V> {
    companion object { private const val serialVersionUID = -1390902873381165909L }

    @Suppress("UNCHECKED_CAST")
    override fun getTableValue(key: Any): V = (table.getOrNull(key) ?: code.invoke().also { table[key] = it }) as V

    override fun setTableValue(key: Any, value: V) { table[key] = value }
}

class ForcedDepthTableDelegate<T : Any>(
    private val depth: Int,
    private val table: CompositeTable<T>
) : CompositeTable<T> by table, TableDelegate<T> {
    companion object { private const val serialVersionUID = -1390902873381165900L }

    override fun setTableValue(key: Any, value: T) { this.setToSkippedNode(key, value, depth) }

    override fun getTableValue(key: Any): T = getFromSkippedNode(key, depth = depth)?: throw FastElementNotFoundException
}