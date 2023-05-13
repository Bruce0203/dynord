package prevayler

import org.prevayler.Transaction
import table.MutableTable
import java.util.*

class TableTransaction<V : Any>(private val pair: Pair<Any, V>) : Transaction<MutableTable<V>> {
    companion object { private const val serialVersionUID = -1390902873381265909L }

    override fun executeOn(prevalentSystem: MutableTable<V>, executionTime: Date) {
        println("Transaction")
        prevalentSystem[pair.first] = pair.second
    }
}