package coding.practice.test

class InlineTest {
    fun test(){
        testInLineFunction("Haha")
//        Member("").test2()
        val listener: Listener = {

        }
        val person: Person = "Tom"
    }
}

typealias Listener = (params: String)->Unit
typealias Person = String

//inline class Member(val name: String){
//    fun speak(){
//        println(name)
//    }
//
//    inline fun test2(){
//        println("inline in inline 2")
//    }
//}

inline fun testInLineFunction(name: String){
    println("name")
    println("name2")
}