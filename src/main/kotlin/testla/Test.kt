package testla

open class Base { }

class Derived : Base() { }

open class BaseCaller {
    open fun Base.printFunctionInfo() {
        println("Base extension function in BaseCaller")
    }

    open fun Derived.printFunctionInfo() {
        println("Derived extension function in BaseCaller")
    }

    fun call(b: Base) {
        b.printFunctionInfo()   // 调用扩展函数
    }
}

class DerivedCaller: BaseCaller() {

    override fun Derived.printFunctionInfo() {
        println("Derived extension function in DerivedCaller")
    }

    override fun Base.printFunctionInfo() {
        println("Base extension function in DerivedCaller")
    }
}

open class Parent

class Child: Parent()

fun Parent.println(){
    println("ext Parent")
}
fun Child.println(){
    println("ext Child")
}

fun main() {
    BaseCaller().call(Base())   // “Base extension function in BaseCaller”
    DerivedCaller().call(Base())  // “Base extension function in DerivedCaller”——分发接收者虚拟解析
    DerivedCaller().call(Derived())  // “Base extension function in DerivedCaller”——扩展接收者静态解析

    val temp: Parent = Child()
    temp.println()
}