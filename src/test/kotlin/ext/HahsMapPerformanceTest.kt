package ext

import util.newSafeMap
import java.util.*
import kotlin.system.measureTimeMillis

//TODO not only GET test, lets do SET test either please?

val map = newSafeMap<Any, String>()
lateinit var lastRandom: Any
val random get() = UUID.randomUUID().toString().apply { lastRandom = this }

fun main() {
    repeat(100000) { map[random] = random }
    val map1 = (0..100000).map { random }
    println(measureTimeMillis { map1.forEach { if (map[it] !== null) throw Exception("Wrong!") } })
}