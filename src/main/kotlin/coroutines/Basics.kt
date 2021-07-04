package coroutines

import kotlinx.coroutines.*
import kotlin.concurrent.thread

class Basics {
    fun run() {
        GlobalScope.launch {
            delay(1000)
            println("World")
        }
        print("Hello ")
        Thread.sleep(2000)
        println("Run1 Done")
    }

    fun run2() {
        GlobalScope.launch {
            delay(1000)
            println("World")
        }
        print("Hello ")
        runBlocking {
            delay(2000)
            println("Run2 Done")
        }
    }

    fun run3() = runBlocking {
        GlobalScope.launch {
            delay(1000)
            println("World")
        }
        print("Hello ")
        delay(2000)
        println("Run3 Done")
    }

    /**
     * 显式等待子协程
     */
    fun run4() = runBlocking {
        val worldPrinter = GlobalScope.launch {
            delay(1000)
            println("World")
        }
        print("Hello ")
        // 等在子协程
        worldPrinter.join()
        println("Run4 Done")
    }

    /**
     * 隐式等待子协程（结构化并发）
     * 在 runBlocking 中直接调用 launch 可以新起一个新的子协程并无需手动 join 也可以等待其结果
     */
    fun run5() = runBlocking {
        launch {
            delay(1000)
            println("World")
            println("Run5 Done")
        }
        print("Hello ")
    }

    fun run6() = runBlocking { // this: CoroutineScope
        launch {
            delay(200L)
            println("Task from runBlocking")
        }
        // 挂起当前线程
        coroutineScope { // 创建一个协程作用域
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
        }

        println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
    }

    /**
     * 在一个 Block 中运行的 launch 会同时启动
     */
    fun run7() = runBlocking {
        launch {
            delay(200)
            println("200")
        }
        launch {
            delay(400)
            println("400")
        }
        launch {
            delay(100)
            println("100")
        }
        launch {
            delay(300)
            println("300")
        }
    }

    fun run8(){
        runBlocking {
            doSomethingDelay()
        }
    }

    /**
     * 抽象成方法
     */
    suspend fun doSomethingDelay(){
        delay(2000)
        println("Some Thing Delay Done")
    }

    /**
     * 协程轻量
     */
    fun run9() = runBlocking {
        var counter = 0
        repeat(100000){
            launch {
                counter++
                println("$counter")
            }
        }
    }

    fun run10() {
        GlobalScope.launch {
            repeat(100000) {
                delay(1000)
                println("Sleeping...")
            }
        }
        Thread.sleep(3000)
        println("run 10 Done")
    }

}