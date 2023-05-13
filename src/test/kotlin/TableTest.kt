import io.github.bruce0203.dynord.prevalent.prevalent
import io.github.bruce0203.dynord.prevalent.save
import io.github.bruce0203.dynord.table.*
import io.github.bruce0203.dynord.table.Collections
import java.util.*
import kotlin.collections.HashSet

interface Elemental<C> {
    var joined: C
}
interface Containable<E> {
    var joinedPlayers: HashSet<E>
}

class GamePlayer(table: Row) : Entity(table), Elemental<Game> {
    companion object { const val serialVersionUID = 149857934791L }
    override var joined: Game by table
}
class Game(table: Row) : Entity(table), Containable<GamePlayer> {
    companion object { const val serialVersionUID = 149857934791L }
    override var joinedPlayers: HashSet<GamePlayer> by table lazy { HashSet<GamePlayer>() }
}

val games by prevalent(Collections() facade ::Game)
val gamePlayers by prevalent(Collections() facade ::GamePlayer)

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

fun main() {
    repeat (100){
        val game = createNewGame()
        val player = UUID.randomUUID()
        joinGame(player, game)
        println(games.getAll().size)
        ::games.save()
        ::gamePlayers.save()
    }

}

