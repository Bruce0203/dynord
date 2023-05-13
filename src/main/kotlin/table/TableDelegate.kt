package table

import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
operator fun <R : Any> Table<*>.getValue(thisRef: Any?, property: KProperty<*>): R {
    return this[property] as R
}

operator fun <R : Any> MutableTable<R>.setValue(thisRef: Any?, property: KProperty<*>, value: R) {
    this[property] = value
}

class LazyTableDelegate<T : Any>(
    private val table: MutableTable<T>,
    private val code: () -> T
) : MutableTable<T> by table {
    companion object { private const val serialVersionUID = -1390902873381165909L }

    @Suppress("UNCHECKED_CAST")
    operator fun <R> getValue(thisRef: Any?, property: KProperty<*>): R {
        val getOrNull = getOrNull(property)
        return (getOrNull ?: code.invoke().also { this[property] = it }) as R
    }
}