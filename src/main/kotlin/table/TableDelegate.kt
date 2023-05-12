package table

import util.FastElementNotFoundException
import kotlin.reflect.KProperty

object TableDelegate {
    @Suppress("UNCHECKED_CAST")
    operator fun <T : TableVisitor<out MutableTable<out Any>>, R : Any> getValue(thisRef: T, property: KProperty<*>): R {
        return thisRef.table[property.hashCode()] as? R?: throw FastElementNotFoundException
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T : TableVisitor<out MutableTable<T2>>, R: Any, T2 : Any> setValue(thisRef: T, property: KProperty<*>, value: R) {
        thisRef.table[property.hashCode()] = value as T2
    }
}

class LazyTableDelegate<R : Any>(private val code: () -> R) {
    @Suppress("UNCHECKED_CAST")
    operator fun <T2 : MutableTable<T3>, T3 : Any> getValue(thisRef: TableVisitor<T2>, property: KProperty<*>): R {
        return (thisRef.table.getOrNull(property)?: code.invoke().also { thisRef.table[property] = it as T3 }) as R
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T2 : MutableTable<T3>, T3 : Any> setValue(thisRef: TableVisitor<T2>, property: KProperty<*>, value: R) {
        thisRef.table[property] = value as T3
    }
}