package io.github.bruce0203.dynord.util

/**
 * fast throwing for performance - skip stacktrace generation
 */
object FastElementNotFoundException : Throwable("stacktrace not provided due to performance") {
    init { stackTrace = emptyArray() }
}
//val FastElementNotFoundException get() = Throwable()