import java.util.UUID

fun main() {
    val list = listOf("", UUID.randomUUID(), 123)
    val times = 100_000_000
    measureTime("upcast", times) {
        list[0] as String
        list[1] as UUID
        list[2] as Int
    }
}