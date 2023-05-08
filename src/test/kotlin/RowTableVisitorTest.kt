import table.*

val Games = tableFacade(CollectionTable(), ::Game)
val GamePlayers = tableFacade(CollectionTable(), ::GamePlayer)

data class GamePlayer(override val table: RowTable) : TableVisitor<RowTable> {
    var joined: Game by TableDelegate
}

data class Game(override val table: RowTable) : TableVisitor<RowTable> {
    val joinedPlayers: MutableList<GamePlayer> by lazy { ArrayList() }
}

fun main() {
    val gamePlayer = GamePlayers["Jimmy"]
    val game = Games["Game1"]
    measureTime("", 5_000) {
        gamePlayer.joined = game
    }
}
