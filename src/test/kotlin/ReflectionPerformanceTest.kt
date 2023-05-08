class TestClass(val name: String) {
    override fun toString() = name
}

fun main() {
    val times = 10_000_000
    measureTime("empty block", times) { }
    measureTime("property call", times) {
        TestClass("").javaClass.simpleName
    }
}