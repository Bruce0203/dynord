import kotlin.system.measureTimeMillis

fun measureTime(
    name: String,
    times: Int = Int.MAX_VALUE/10000,
    send: Boolean = true,
    code: () -> Unit,
) : Long {
    val millis = measureTimeMillis { repeat(times) { code() } }
    if (send) println("$name (${millis}ms)")
    return millis
}

