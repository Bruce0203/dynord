package ext

import kotlin.system.measureTimeMillis

class GetClassPerformanceTest
fun main() {
    val obj = GetClassPerformanceTest()
    repeat(100) { obj.javaClass.simpleName }
    println(obj.hashCode())
    println(GetClassPerformanceTest::class.java.hashCode())
    println(measureTimeMillis{ repeat(100) { obj.javaClass.simpleName } })

}