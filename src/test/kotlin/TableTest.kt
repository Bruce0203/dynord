import table.*

object TableTest {

    data class Game(override val table: RowTable) : TableVisitor<RowTable> {
        var isPlaying: Boolean by TableDelegate
    }

    private val games = CollectionTable(::NodeTable) facade::Game

    @JvmStatic
    fun main(args: Array<String>) {
        val testGame = games["testGame"]
        testGame.isPlaying = true
        println(testGame.isPlaying)
    }

}
