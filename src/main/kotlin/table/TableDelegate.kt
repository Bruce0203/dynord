package table

import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
operator fun <R : Any> Table<*>.getValue(thisRef: Any?, property: KProperty<*>): R {
    return this[property.hashCode()] as R
}

operator fun <R> MutableTable<R>.setValue(thisRef: Any?, property: KProperty<*>, value: R) {
    this[property.hashCode()] = value
}

class LazyTableDelegate<T : Any>(
    private val table: MutableTable<T>,
    private val code: () -> T
) : MutableTable<T> by table {
    @Suppress("UNCHECKED_CAST")
    operator fun <R> getValue(thisRef: Any?, property: KProperty<*>): R {
        val getOrNull = getOrNull(property.hashCode())
        return (getOrNull ?: code.invoke().also { this[property] = it }) as R
    }
}