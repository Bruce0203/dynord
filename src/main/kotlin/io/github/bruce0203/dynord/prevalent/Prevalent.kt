package io.github.bruce0203.dynord.prevalent

import org.prevayler.Prevayler
import org.prevayler.PrevaylerFactory
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

class Prevalent<T>(private val value: T) {
    internal val prevalent: Prevayler<T> = PrevaylerFactory<T>().apply {
        configurePrevalentSystem(value)
        configureTransientMode(true)
    }.create()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = prevalent.prevalentSystem()
}

fun KProperty0<*>.save() {
    isAccessible = true
    val delegate = getDelegate() as? Prevalent<*>?: throw Exception("not delegated")
    delegate.prevalent.takeSnapshot()
}

inline fun <reified T : Any> prevalent(value: T): Prevalent<T> = Prevalent(value)