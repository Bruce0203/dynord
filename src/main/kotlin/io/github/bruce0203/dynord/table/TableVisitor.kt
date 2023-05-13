package io.github.bruce0203.dynord.table

import java.io.Serializable

typealias Collections = NodeTable<Row>
typealias Row = NodeTable<Any>

interface TableVisitor<T> : Serializable {
    val table: T
}

open class Entity(override val table: Row) : TableVisitor<Row> {
    override fun hashCode(): Int {
        return table.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Entity

        if (table != other.table) return false

        return true
    }
}