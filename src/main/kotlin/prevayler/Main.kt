package prevayler

import org.prevayler.PrevaylerFactory
import table.*
import java.util.*

data class Game(override val table: RowTable) : TableVisitor<RowTable> {
    companion object { private const val serialVersionUID = -7690903879381167909L }
    var testValue: Boolean by table
}

val Games = CollectionTable(::NodeTable) facade::Game

fun newGame(testValue : Boolean) = Game(RowTable()).apply { this.testValue = testValue }

fun main() {
    val prevalently = PrevaylerFactory.createPrevayler(Games)
    val tableTransaction = TableTransaction(UUID.randomUUID() to newGame(true))
    val before = System.currentTimeMillis()
    repeat(2) { prevalently.execute(tableTransaction) }
    val after = System.currentTimeMillis()
    println(after - before)
    println(prevalently.prevalentSystem().getAll().first().run { testValue })
    prevalently.takeSnapshot()
}
