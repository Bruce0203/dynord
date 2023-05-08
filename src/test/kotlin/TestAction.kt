import kotlin.reflect.KProperty

//componentService - LIST
//namespace -- LIST
//key-value -- MAP

@Suppress("UNCHECKED_CAST")
open class ActiveRecord(
    
) {

    protected val getting get() = this

    operator fun <T> getValue(thisRef: Any?, property: KProperty<*>): T {
        return property.call() as T
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {

    }

}


class TestAction : ActiveRecord() {
    val junkVariable: Any by getting

}