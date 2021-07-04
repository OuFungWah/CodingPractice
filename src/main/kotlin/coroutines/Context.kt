package coroutines

import kotlinx.coroutines.*

class Context {
    fun run1() {
        runBlocking(Dispatchers.Default) { println("Default is Running on ${Thread.currentThread().name}") }
        runBlocking(Dispatchers.IO) { println("IO is Running on ${Thread.currentThread().name}") }
        // Main 调度器需要指定，否则会报 Main 找不到的问题
//        runBlocking(Dispatchers.Main) { println("Main is Running on ${Thread.currentThread().name}") }
        runBlocking(Dispatchers.Unconfined) { println("Unconfined is Running on ${Thread.currentThread().name}") }
        runBlocking(newSingleThreadContext("TestingThread")) { println("newSingleThreadContext is Running on ${Thread.currentThread().name}") }
        runBlocking(newFixedThreadPoolContext(4, "TestingThreadPool")) {
            launch {
                println("launch1 is Running on ${Thread.currentThread().name}")
                launch {
                    println("launch1-1 is Running on ${Thread.currentThread().name}")
                    delay(4000)
                }
                launch {
                    println("launch1-2 is Running on ${Thread.currentThread().name}")
                    delay(4000)
                }
                launch {
                    println("launch1-3 is Running on ${Thread.currentThread().name}")
                    delay(4000)
                }
                launch {
                    println("launch1-4 is Running on ${Thread.currentThread().name}")
                    delay(4000)
                }
                delay(4000)
            }
            launch {
                println("launch2 is Running on ${Thread.currentThread().name}")
                delay(4000)
            }
            launch {
                println("launch3 is Running on ${Thread.currentThread().name}")
                delay(4000)
            }
            launch {
                println("launch4 is Running on ${Thread.currentThread().name}")
                delay(4000)
            }
        }
    }

    fun run2() {
        runBlocking {
            // 非受限调度器，
            launch(Dispatchers.Unconfined) {
                println("Coroutine with unconfined before delay. ${Thread.currentThread().name}")
                delay(500)
                // 非受限的协程在第一次被挂起以后将协程切到 Kotlin 默认的执行者线程中
                // todo 目的是啥呢？
                println("Coroutine with unconfined after delay. ${Thread.currentThread().name}")
                delay(500)
                println("Coroutine with unconfined after delay second time. ${Thread.currentThread().name}")
            }
            launch {
                println("Coroutine with no specify before delay. ${Thread.currentThread().name}")
                delay(1000)
                println("Coroutine with no specify after delay. ${Thread.currentThread().name}")
            }
        }
    }

    fun run3() {
        runBlocking {
            // 在不同的协程中跳动
            newSingleThreadContext("ctx1").use { ctx1 ->
                newSingleThreadContext("ctx2").use { ctx2 ->
                    runBlocking(ctx1) {
                        delay(1000)
                        println("Coroutine ctx1. ${Thread.currentThread().name}")
                        withContext(ctx2){
                            delay(500)
                            println("do Something in ctx2. ${Thread.currentThread().name}")
                        }
                        println("Back to ctx1. ${Thread.currentThread().name}")
                    }
                }
            }
            delay(2000)
            println("Done")
        }
    }

}