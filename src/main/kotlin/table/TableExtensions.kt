package table

import util.fastForEach

infix fun <T1, T2 : TablePresenter<T3>, T3> T2.to(code: (T2) -> T1): T1 = code(this)

infix fun <T> InheritableTablePresenter<T>.child(table: InheritableTablePresenter<T>) = addChild(table)

fun <T> InheritableTablePresenter<T>.children(vararg table: InheritableTablePresenter<T>) = table.fastForEach(::addChild)

