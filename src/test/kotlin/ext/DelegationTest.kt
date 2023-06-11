package ext

fun main() {
    println(MyInstance())
}

interface MyInterface {
    fun method1()
}

class MyClass : MyInterface {

    override fun method1() {
        println("method1")
    }
}

class MyInstance(
    private val myDelegation: MyDelegation = MyDelegation()

) : MyInterface by myDelegation {
    override fun method1() {
        println("MyInstance")
    }
}

class MyDelegation : MyInterface by MyClass() {
    override fun method1() {
        println("MyDelegation")
    }
}
