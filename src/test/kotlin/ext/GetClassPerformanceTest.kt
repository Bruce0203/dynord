package ext

import kotlin.system.measureTimeMillis

class GetClassPerformanceTest
fun main() {
    println(measureTimeMillis{ repeat(100) { GetClassPerformanceTest::class.simpleName } })

}