@file:Suppress("UNCHECKED_CAST")

package io.github.bruce0203.dynord.table

import io.github.bruce0203.dynord.util.FastElementNotFoundException
import kotlin.reflect.KProperty

open class TableDelegate<T : Any>(private val table: CompositeTable<Any>) : CompositeTable<Any> by table {
    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
        set(property.hashCode(), value)

    operator fun getValue(thisRef: Any, property: KProperty<*>): T =
        this[property.hashCode()] as T

}

class LazyTableDelegate<T : Any>(
    private val table: CompositeTable<Any>,
    private val code: () -> T
) : TableDelegate<T>(table) {
    override fun get(key: Any): T = (table.getOrNull(key) ?: code.invoke().also { table[key] = it }) as T

    override fun set(key: Any, newValue: Any) { table[key] = newValue }
}

class ForcedDepthTableDelegate<T : Any>(
    private val depth: Int,
    private val table: CompositeTable<Any>
) : TableDelegate<T>(table) {
    override fun get(key: Any): T = (table.getFromSkippedNode(key, depth = depth)?: throw FastElementNotFoundException) as T

    override fun set(key: Any, newValue: Any) { table.setToSkippedNode(key, newValue, depth) }
}