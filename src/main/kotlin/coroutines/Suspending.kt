import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class Suspending {
    fun run1() {
        runBlocking {
            val one = calculateOne()
            val two = calculateTwo()
            print("result = ${one + two}")
        }
    }

    suspend fun calculateOne(): Int {
        delay(2000)
        return 10
    }

    suspend fun calculateTwo(): Int {
        delay(1500)
        return 21
    }

    suspend fun calculateTwoException(): Int {
        delay(1500)
        throw Exception("Error")
        return 21
    }

    fun run2() {
        runBlocking {
            val one = async { calculateOne() }
            val two = async { calculateTwo() }
            one.start()
            two.start()
            println("result = ${one.await() + two.await()}")
        }
    }

    fun run3() {
        runBlocking {
            val one = async { calculateOne() }
            // 子协程异常会导致父协程崩溃
            val two = async { calculateTwoException() }
            one.start()
            two.start()
            println("result = ${one.await() + two.await()}")
        }
        println("Done run3")
    }
}