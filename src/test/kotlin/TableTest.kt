import serialize.deserialize
import serialize.serialize
import table.*
import java.io.File


class Game(override val table: RowTable) : TableVisitor<RowTable> {
    var joinedPlayers: MutableList<Any> by table lazy { listOf("asdf") }
}

val games = CollectionTable(::NodeTable) facade::Game

fun main() {
    val sam = games["Sam"]
    sam.joinedPlayers = mutableListOf("af")
    println(games["Sam"].joinedPlayers)
    val file = File("test.txt")
    games["Sam"].joinedPlayers
    serialize(games, file)
    val games = deserialize<MutableTableFacade<CollectionTable, Game>>(file)
    println(games["Sam"].joinedPlayers)
}