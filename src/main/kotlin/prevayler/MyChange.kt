package prevayler

import org.prevayler.Transaction
import java.util.*

data class Container(
    val name: String,
)

class MyChange : Transaction<Any> {
    override fun executeOn(prevalentSystem: Any?, executionTime: Date?) {

    }
}

