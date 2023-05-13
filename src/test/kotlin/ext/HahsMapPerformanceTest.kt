package ext

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis

//TODO not only GET test, lets do SET test either please?

val map = ConcurrentHashMap<Any, Any>()
val random get() = UUID.randomUUID()!!

fun main() {
    val times = 100_000
    repeat(1000000) { map[Any().hashCode()] = Any().hashCode() }
    println(measureTimeMillis{ repeat(times){ map[Any().hashCode()] = Any().hashCode() } })
    val generatedRandoms = (0..times).map { random }
    println(measureTimeMillis { generatedRandoms.forEach { if (map[it] !== null) throw Exception("Wrong!") } })
}