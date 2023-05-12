package table

import util.FastElementNotFoundException
import kotlin.reflect.KProperty

interface TableVisitor<T> {

    val table: T

    fun <R : Any> lazy(code: () -> R) = LazyTableDelegate(code)

    infix fun <R> to(code: (T) -> R): R = code(table)

}

object TableDelegate {
    @Suppress("UNCHECKED_CAST")
    operator fun <T : TableVisitor<out MutableTablePresenter<out Any>>, R : Any> getValue(thisRef: T, property: KProperty<*>): R {
        return thisRef.table[property.hashCode()] as? R?: throw FastElementNotFoundException
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T : TableVisitor<out MutableTablePresenter<T2>>, R: Any, T2 : Any> setValue(thisRef: T, property: KProperty<*>, value: R) {
        thisRef.table[property.hashCode()] = value as T2
    }

}

class LazyTableDelegate<R : Any>(
    private val code: () -> R
) {
    @Suppress("UNCHECKED_CAST")
    operator fun <T2 : MutableTablePresenter<T3>, T3 : Any> getValue(thisRef: TableVisitor<T2>, property: KProperty<*>): R {
        return (thisRef.table.getOrNull(property)?: code.invoke().also { thisRef.table[property] = it as T3 }) as R
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T2 : MutableTablePresenter<T3>, T3 : Any> setValue(thisRef: TableVisitor<T2>, property: KProperty<*>, value: R) {
        thisRef.table[property] = value as T3
    }
}

fun <T : TableVisitor<InheritableTablePresenter<T2>>, T2> T.addChild(other: T) = table.addChild(other.table)
