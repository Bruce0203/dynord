fun main() {

    val times = 1_000_000

    measureTime("empty code", times) {  }

    measureTime("try catch block", times) { try { } catch (_: Throwable) {} }

    measureTime("throwing without stacktrace generation", times) {
        Throwable().let { try { throw it } catch (_: Throwable) {} }
    }

    measureTime("throwing with stacktrace generation", times) {
        try { throw Throwable() } catch (_: Throwable) {}
    }
}