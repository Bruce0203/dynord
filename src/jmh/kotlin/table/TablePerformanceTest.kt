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

    data class GamePlayer(override val table: RowTable) : TableVisitor<RowTable> {
        var joined: Game by table
    }

    data class Game(override val table: RowTable) : TableVisitor<RowTable> {
        var joinedPlayers: MutableList<GamePlayer> by table lazy { ArrayList<GamePlayer>() }
    }

    private val games = CollectionTable() dummy::RowTable facade::Game
    private val gamePlayers = CollectionTable() dummy::RowTable facade::GamePlayer

    init {
        gamePlayers["Jimmy"] = GamePlayer(RowTable())
        games["Jimmy"] = Game(RowTable())
    }
    private val game = games["Jimmy"]
    private val emptyList = ArrayList<GamePlayer>()


    private infix fun <T : MutableTable<E>, E> T.dummy(block: () -> E) = apply {
        repeat(100) { (set(UUID.randomUUID(), block())) }
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    fun genVisitorAndReferProps() {
        val gamePlayer = gamePlayers["Jimmy"]
        gamePlayer.joined = game
        gamePlayer.joined
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    fun genVisitorAndReferLazyProps() {
        val games = games["Jimmy"]
        games.joinedPlayers = emptyList
        games.joinedPlayers
    }

}

