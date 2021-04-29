package coding.practice.test

import java.lang.Exception

fun main(){
    (ExceptionTest::class.java)
}
class ExceptionTest{
    fun test(): Int{
        throw Exception("mm")
        return 0
    }
}