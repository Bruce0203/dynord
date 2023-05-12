import table.*

class Game(override val table: RowTable) : TableVisitor<RowTable> {
    var joinedPlayers: List<Any> by tableLazy(::ArrayList)
}
val games = CollectionTable(::NodeTable) facade::Game

fun main() {
    val sam = games["Sam"]
    sam.joinedPlayers = listOf()
    println(sam.joinedPlayers)
}