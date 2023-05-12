package table

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import table.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class TablePerformanceTest {

    data class GamePlayer(override val table: RowTable) : TableVisitor<RowTable> {
        var joined: Game by TableDelegate
    }

    data class Game(override val table: RowTable) : TableVisitor<RowTable> {
        val joinedPlayers: MutableList<GamePlayer> by lazy { ArrayList() }
    }

    private val Games = CollectionTable() facade::Game
    private val GamePlayers = CollectionTable() facade::GamePlayer

    @Benchmark
    fun genVisitorAndReferProps() {
        val gamePlayer = GamePlayers["Jimmy"]
        gamePlayer.joined
    }

    @Benchmark
    fun genVisitorAndReferLazyProps() {
        val games = Games["Jimmy"]
        games.joinedPlayers
    }

}