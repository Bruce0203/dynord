import table.*


class Game(override val table: RowTable) : TableVisitor<RowTable> {
    var joinedPlayers: List<String> by table lazy { listOf("asdf") }
}
val games = CollectionTable(::NodeTable) facade::Game

fun main() {
    val sam = games["Sam"]
    sam.joinedPlayers = listOf("a")
    println(sam.joinedPlayers)
}