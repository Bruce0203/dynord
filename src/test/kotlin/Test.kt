import io.github.bruce0203.dynord.table.*
import java.util.*
import kotlin.system.measureTimeMillis

class PersonMeta(override val table: Row) : TableVisitor<Row> {
    var age by value<Int>()
}

class Person(override val table: Row) : TableVisitor<Row> {
    val meta by lazy { PersonMeta(Row()) }
}

fun main() {
    val table = Row()
    val uuids = (0..150_000).map { UUID.randomUUID().also { table[it] = "" } }.toList()
    repeat(50) {
        val shuffledUUID = uuids.shuffled()
        println(measureTimeMillis{ shuffledUUID.forEach { table[it] } })
    }
}