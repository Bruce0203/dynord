package table

import org.openjdk.jmh.annotations.*
import table.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Suppress("unused")
open class TablePerformanceTest {

    data class GamePlayer(override val table: Row) : Entity {
        var joined: Game by table
    }

    data class Game(override val table: Row) : Entity {
        var joinedPlayers: List<GamePlayer> by table lazy { ArrayList<GamePlayer>() }
    }

    private val games = Collections() facade::Game
    private val gamePlayers = Collections() facade::GamePlayer
    private val gameKey = 1
    private val game = Game(Row()).apply { games[gameKey] = this }
    private val singletonList = emptyList<GamePlayer>()

    @Fork(value = 1, warmups = 1)
    @Benchmark
    fun setNewRow() {
        games[Any().hashCode()] = Game(Row())
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    fun getOrNullEntity() {
        games.getOrNull(Any().hashCode())
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    fun getProp() {
        game.joinedPlayers
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    fun setProp() {
        game.joinedPlayers = singletonList
    }

}

