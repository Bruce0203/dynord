@file:Suppress("unused")
package io.github.bruce0203.dynord.table

infix fun <T1, T2 : Table<T3>, T3> T2.to(code: (T2) -> T1): T1 = code(this)

infix fun <T3 : Any, T : CompositeTable<T3>, T2 : CompositeTableFacade<T, *>> T2.child(table: CompositeTableFacade<T, *>): T2 =
    apply { this.table.addChild(table.table) }

fun <T : Any> CompositeTable<T>.children(vararg table: CompositeTable<T>) = table.forEach(::addChild)

infix fun <T : Any> MutableTable<T>.lazy(code: () -> T) = LazyTableDelegate(this, code)

infix fun <T, R> TableVisitor<T>.to(code: (T) -> R): R = code(table)

infix fun <A : MutableTable<T>, T : Table<*>, V : TableVisitor<T>>
        A.facade(code: (T) -> V) = MutableTableFacade(this, code) { it.table }

infix fun <A : CompositeTable<T>, T : Table<*>, V : TableVisitor<T>>
        A.facade(code: (T) -> V) = NodeTableFacade(this, code) { it.table }
