import io.github.bruce0203.dynord.prevalent.prevalent
import io.github.bruce0203.dynord.prevalent.save
import io.github.bruce0203.dynord.table.*
import io.github.bruce0203.dynord.table.Collections
import java.util.*
import kotlin.collections.HashSet

class GamePlayer(table: Row) : Entity(table) {
    companion object { const val serialVersionUID = 8463017483753869000L }

    var joined by table value ::Game
    var isPlaying by table lazy { false }
}

class Game(table: Row) : Entity(table) {
    companion object { const val serialVersionUID = 8463017483753869001L }

    val joinedPlayers by table lazy { HashSet<GamePlayer>() }
    var isPlaying by table lazy { false }
}

open class Handler(table: Row) : Entity(table) {
    var isEnabled by table depth(3) lazy { true }
}

class GameHandler(table: Row) : Handler(table) {
    //todo depth customized property delegate
}

val handlers = Collections() facade ::Handler
val games by prevalent { Collections() facade::Game child handlers }
val gamePlayers by prevalent { Collections() facade::GamePlayer child games }

fun joinGame(uuid: UUID, game: Game) {
    val player = GamePlayer(Row())
    player.joined = game
    gamePlayers[uuid] = player
    game.joinedPlayers.add(player)
}

fun leftGame(uuid: UUID) {
    gamePlayers.remove(uuid)
}

fun createNewGame(): Game = Game(Row()).also { games[Any().hashCode()] = it }

fun initializeHandlers() {
    handlers[GameHandler::javaClass] = Handler(Row())
}

fun main() {
    initializeHandlers()
    repeat (10) {
        println(games.getAll().size)
        val game = createNewGame()
        val player = UUID.randomUUID()
        joinGame(player, game)
        println((game to ::GameHandler).isEnabled)
    }
    ::games.save()
    ::gamePlayers.save()
    println(games.getAll().first().table.getAll())
}

