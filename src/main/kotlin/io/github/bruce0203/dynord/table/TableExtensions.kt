@file:Suppress("unused", "unused_parameter")
package io.github.bruce0203.dynord.table

infix fun <T1, T2 : Table<T3>, T3> T2.to(code: (T2) -> T1): T1 = code(this)

infix fun <T3 : Any, T : CompositeTable<T3>, T2 : CompositeTableFacade<T, *>> T2.child(table: CompositeTableFacade<T, *>): T2 =
    apply { this.table.addChild(table.table) }

fun <T : Any> CompositeTable<T>.children(vararg table: CompositeTable<T>) = table.forEach(::addChild)

infix fun <A, T : Any, V : T> MutableTable<T>.value(code: (A) -> V) =
    MutableTableDelegate<T, V>(this)
infix fun <T : Any, V : T> MutableTable<T>.lazy(code: () -> V) = LazyTableDelegate(this, code)
infix fun <T : Any, V : T> CompositeTable<T>.depth(depth: Int) =
    ForcedDepthTableDelegate(depth, this)

infix fun <T, R> TableVisitor<T>.to(code: (T) -> R): R = code(table)

infix fun <A : MutableTable<T>, T : Table<*>, V : TableVisitor<T>>
        A.facade(code: (T) -> V) = MutableTableFacade(this, code) { it.table }

infix fun <A : CompositeTable<T>, T : Table<*>, V : TableVisitor<T>>
        A.facade(code: (T) -> V) = NodeTableFacade(this, code) { it.table }
