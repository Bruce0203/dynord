import table.*
import kotlin.system.measureTimeMillis

class Game(override val table: Row) : Entity {
    var joinedPlayers: MutableList<Any> by table lazy { listOf("asdf") }
}

val games = Collections(::Row) facade::Game

fun main() {
    repeat(10000) { games[Any().hashCode()] }
    games[1]
    repeat(100) {
        println(measureTimeMillis {
            repeat(1000) { games[1].joinedPlayers }
        })
    }
}