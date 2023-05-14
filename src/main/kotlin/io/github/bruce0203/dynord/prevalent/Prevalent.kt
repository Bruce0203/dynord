package io.github.bruce0203.dynord.prevalent

import org.prevayler.Prevayler
import org.prevayler.PrevaylerFactory
import java.io.File
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

typealias PrevalentMap = HashMap<File, Prevayler<*>>
object GlobalPrevalent {
    val prevalent = PrevalentMap()

    fun <T> newPrevalent(prevalent: T, directory: String) =
        PrevaylerFactory<T>().apply {
            configurePrevalentSystem(prevalent)
            configurePrevalenceDirectory(directory)
            configureTransientMode(true)
        }.create()!!

    operator fun <T> get(key: KProperty<*>, new: () -> T): Prevayler<T> {
        return get(File("Prevalence${File.separator}${key.name}"), new)
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: File, new: () -> T): Prevayler<T> {
        return prevalent[key] as? Prevayler<T>?: newPrevalent(new(), key.canonicalPath)
    }
}

class Prevalent<T>(private val new: () -> T) {
    internal var cachedValue: Prevayler<T>? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return (cachedValue?: GlobalPrevalent[property, new].also { cachedValue = it }).prevalentSystem()
    }
}

fun KProperty0<*>.save() {
    isAccessible = true
    val delegate = getDelegate() as? Prevalent<*>?: throw Exception("not delegated")
    delegate.cachedValue?.takeSnapshot()?: Exception("cannot save due to not loaded yet")
}

inline fun <reified T : Any> prevalent(noinline new: () -> T): Prevalent<T> = Prevalent(new)
