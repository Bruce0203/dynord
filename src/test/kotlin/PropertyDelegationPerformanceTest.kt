import java.util.*
import java.util.stream.IntStream
import kotlin.reflect.KProperty

class DelegateTest {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Any {
        return Any()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Any) {

    }

}

class WithDelegate {
    val delegatedProperty: Any by DelegateTest()
    val adelegatedProperty: Any by DelegateTest()
    val azdelegatedProperty: Any by DelegateTest()
    val azadelegatedProperty: Any by DelegateTest()
    val azaadelegatedProperty: Any by DelegateTest()
    val azaaadelegatedProperty: Any by DelegateTest()
}

class WithoutDelegate

fun main() {
    val times = 100_000
    repeat(2) {
        measureTime("no delegate", times) { IntStream.range(0, 100).forEach { WithoutDelegate() } }
        measureTime("define delegate", times) { WithDelegate() }
        measureTime("refer delegate", times) { WithDelegate().delegatedProperty }
    }
}
