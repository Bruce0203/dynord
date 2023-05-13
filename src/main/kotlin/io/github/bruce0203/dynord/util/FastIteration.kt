@file:Suppress("unused")
package io.github.bruce0203.dynord.util

inline fun <T> List<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

inline fun <T> Array<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

inline fun <T, R> List<T>.fastFirstOrNull(predicate: (T) -> R?): R? {
    fastForEach { element -> predicate(element)?.also { return it } }
    return null
}

inline fun <T, R> Array<T>.fastFirstOrNull(predicate: (T) -> R?): R? {
    fastForEach { element -> predicate(element)?.also { return it } }
    return null
}


inline fun <T, R> List<T>.fastFirst(predicate: (T) -> R?): R {
    fastForEach { element -> predicate(element)?.also { return it } }
    throw FastElementNotFoundException
}

inline fun <T, R> Array<T>.fastFirst(predicate: (T) -> R?): R {
    fastForEach { element -> predicate(element)?.also { return it } }
    throw FastElementNotFoundException
}
