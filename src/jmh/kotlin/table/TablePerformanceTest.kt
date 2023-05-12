package table

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import table.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Suppress("unused")
open class TablePerformanceTest {

    data class GamePlayer(override val table: RowTable) : TableVisitor<RowTable> {
        var joined: Game by TableDelegate
    }

    data class Game(override val table: RowTable) : TableVisitor<RowTable> {
        var joinedPlayers: MutableList<GamePlayer> by lazy { ArrayList() }
    }

    private val games = CollectionTable() facade::Game
    private val gamePlayers = CollectionTable() facade::GamePlayer

    private val game = games["Jimmy"]
    val emptyList = ArrayList<GamePlayer>()

    @Benchmark
    fun genVisitorAndReferProps() {
        val gamePlayer = gamePlayers["Jimmy"]
        gamePlayer.joined = game
        gamePlayer.joined
    }

    @Benchmark
    fun genVisitorAndReferLazyProps() {
        val games = games["Jimmy"]
        games.joinedPlayers = emptyList
        games.joinedPlayers
    }

}