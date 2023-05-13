@file:Suppress("unused")
package table

import java.io.Serializable

infix fun <T1, T2 : Table<T3>, T3> T2.to(code: (T2) -> T1): T1 = code(this)

infix fun <T : Any> InheritableTable<T>.child(table: InheritableTable<T>) = addChild(table)

fun <T : Any> InheritableTable<T>.children(vararg table: InheritableTable<T>) = table.forEach(::addChild)

interface TableVisitor<T> : Serializable {
    companion object { private const val serialVersionUID = -1390903873381165909L }

    val table: T
}

infix fun <T : Any> MutableTable<T>.lazy(code: () -> T) =
    LazyTableDelegate(this, code)

infix fun <T, R> TableVisitor<T>.to(code: (T) -> R): R = code(table)

infix fun <A : MutableTable<T>, T : MutableTable<*>, V : TableVisitor<T>> A.facade(code: (T) -> V) =
    MutableTableFacade(this, code) { it.table }