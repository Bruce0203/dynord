package io.github.bruce0203.dynord.table

import java.io.Serializable

typealias Collections = NodeTable<Row>
typealias Row = NodeTable<Any>

interface TableVisitor<T : CompositeTable<Any>> : Serializable {
    val table: T
    fun <T : Any> value() = TableDelegate<T>(table)
    infix fun <A, T : Any> value(code: (A) -> T) = TableDelegate<T>(table)
    infix fun <T : Any> lazy(code: () -> T) = LazyTableDelegate(table, code)
    infix fun depth(depth: Int) = ForcedDepthTableDelegate<Any>(depth, table)
}