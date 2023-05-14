import io.github.bruce0203.dynord.prevalent.prevalent
import io.github.bruce0203.dynord.table.*
import io.github.bruce0203.dynord.table.Collections
import java.util.*

interface Elemental<C> {
    var joined: C
}
interface Containable<E> {
    var joinedPlayers: HashSet<E>
}

class GamePlayer(table: Row) : Entity(table), Elemental<Game> {
    companion object { const val serialVersionUID = 8463017483753869000L }
    override var joined: Game by table
    var isPlaying: Boolean by table lazy { false }
}
class Game(table: Row) : Entity(table), Containable<GamePlayer> {
    companion object { const val serialVersionUID = 8463017483753869001L }
    override var joinedPlayers: HashSet<GamePlayer> by table lazy { HashSet<GamePlayer>() }
    var isPlaying: Boolean by table lazy { false }
}

open class Handler(table: Row) : Entity(table) {
    var isEnabled: Boolean by table lazy { true }
}

class GameHandler(table: Row) : Handler(table) {
    //todo depth customized property delegate
}

val handlers = Collections() facade ::Handler
val games by prevalent(Collections() facade::Game child handlers)
val gamePlayers by prevalent(Collections() facade::GamePlayer child games)

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
}

