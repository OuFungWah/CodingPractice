package coroutines

import kotlinx.coroutines.*
import java.lang.Exception

class CancelCoroutines {

    fun run1() {
        runBlocking {
            val job = launch {
                delay(2000)
                println("Job finish")
            }
            delay(1000)
            job.cancel()
            println("Job Canceled")
        }
    }

    fun run2() {
        runBlocking {
            val job = launch {
                var time = 0
                while (isActive && time < 5) {
                    delay(666)
                    println("${time++}")
                }
            }
            delay(2000)
            job.cancel()
            println("Job canceled.")
        }
    }

    fun run3() {
        runBlocking {
            val job = launch {
                try {
                    repeat(200) {
                        delay(500)
                        println("now repeat to $it")
                    }
                } catch (e: Exception) {
                    // coroutines
                    println("${e.message}")
                } finally {
                    println("do last")
                }
            }
            delay(1000)
            job.cancel()
        }
    }

    fun run4() {
        runBlocking {
            val job = launch {
                // 不可取消的任务
                withContext(NonCancellable) {
                    try {
                        repeat(200) {
                            delay(500)
                            println("now repeat to $it")
                        }
                    } catch (e: Exception) {
                        // coroutines
                        println("${e.message}")
                    } finally {
                        println("do last")
                    }
                }
            }
            delay(1000)
            job.cancel()
        }
    }

    fun run5() {
        runBlocking {
            val job = launch {
                val result = withTimeoutOrNull(2000) {
                    try {
                        delay(2001)
                        return@withTimeoutOrNull 1
                    } catch (e: Exception) {
                        println("Exception ${e.message}")
                    } finally {
                        println("release something")
                    }
                }
                println("result = $result")
            }
            job.join()
        }
    }

}