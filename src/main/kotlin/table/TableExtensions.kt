package table

infix fun <T1, T2 : Table<T3>, T3> T2.to(code: (T2) -> T1): T1 = code(this)

infix fun <T> InheritableTable<T>.child(table: InheritableTable<T>) = addChild(table)

fun <T> InheritableTable<T>.children(vararg table: InheritableTable<T>) = table.forEach(::addChild)

fun <T : TableVisitor<InheritableTable<T2>>, T2> T.addChild(other: T) = table.addChild(other.table)

infix fun <T : MutableTable<*>, V : TableVisitor<T>>
        MutableTable<T>.facade(newValue: (T) -> V) = TableFacade(this, newValue)

fun <R : Any> lazy(code: () -> R) = LazyTableDelegate(code)

infix fun <T, R> TableVisitor<T>.to(code: (T) -> R): R = code(table)