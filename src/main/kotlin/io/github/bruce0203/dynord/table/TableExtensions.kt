@file:Suppress("unused", "unused_parameter")
package io.github.bruce0203.dynord.table

infix fun <T3 : Any, T : CompositeTable<T3>, T2 : CompositeTableFacade<T, *>> T2.child(table: CompositeTableFacade<T, *>): T2 =
    apply { this.table.addChild(table.table) }

fun <T : Any> CompositeTable<T>.children(vararg table: CompositeTable<T>) = table.forEach(::addChild)

fun <T : Any> CompositeTable<Any>.value() = TableDelegate<T>(this)
infix fun <A, T : Any> CompositeTable<Any>.value(code: (A) -> T) = TableDelegate<T>(this)
infix fun <T : Any> CompositeTable<Any>.lazy(code: () -> T) = LazyTableDelegate(this, code)
infix fun CompositeTable<Any>.depth(depth: Int) = ForcedDepthTableDelegate<Any>(depth, this)

infix fun <T : TableVisitor<T3>, T2 : TableVisitor<T3>, T3 : CompositeTable<T4>, T4>
        T.child(child: T2) = child.also { table.addChild(it.table) }

infix fun <A : MutableTable<T>, T : Table<*>, V : TableVisitor<T>>
        A.facade(code: (T) -> V) = MutableTableFacade(this, code) { it.table }

infix fun <A : CompositeTable<T>, T : Table<*>, V : TableVisitor<T>>
        A.facade(code: (T) -> V) = NodeTableFacade(this, code) { it.table }
