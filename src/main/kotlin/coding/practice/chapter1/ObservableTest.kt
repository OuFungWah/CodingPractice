package coding.practice.chapter1

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function3
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.Exception
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun main() {
}

fun rangeTest() {
    Observable.create<Long> { emitter: ObservableEmitter<Long> ->
        // This is the first part when Observable was subscribed
        try {
            // emitter try to send the data to downstream
            emitter.onNext(System.currentTimeMillis())
        } catch (e: Exception) {
            // emitter will send an exception to downstream when there is an error has been caused,
            // this step is optional
            emitter.onError(e)
        }
        // notify downstream the job is totally complete,
        // this step is optional
        emitter.onComplete()
    }.filter { data: Long ->
        // in filter, we judge the data whether it fit to our standard
        data > 0L
    }.map { dataAfterFiltered: Long ->
        // in map, we change the data's format
        "map change $dataAfterFiltered to String! "
    }.subscribe({ dataFinal: String ->
        // do in onNext,
        println("This is the data after all 【$dataFinal】")
    }, { t: Throwable? ->
        // do in onError
        t?.printStackTrace()
    }, {
        // do in Complete
        println("This Observable is completely finished!!")
    })
}

fun createTest() {
    Observable.create(object : ObservableOnSubscribe<String> {
        override fun subscribe(emitter: ObservableEmitter<String>?) {
            try {
                emitter?.onNext("Hello World")
            } catch (e: Exception) {
                emitter?.onError(e)
            }
        }
    }).subscribe(object : Observer<String> {
        override fun onComplete() {
            // do something while onComplete
        }

        override fun onSubscribe(d: Disposable?) {
            // do something while onSubscribe
        }

        override fun onNext(t: String?) {
            // do something while receive data
            println(t)
        }

        override fun onError(e: Throwable?) {
            // do something while error
        }
    })
    val countDownLatch = CountDownLatch(1)
    var emitter: ObservableEmitter<String>? = null
    Observable.create<String> {
        emitter = it
        it.onNext("Hello World")
    }.subscribe({
        println(it)
    }, {
        // do something while error
        println("error")
    }, {
        // do something while onComplete
        println("complete")
    })
    Observable.interval(300, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .subscribe({
            emitter?.onNext("this from other observer $it")
        }, {

        })
    countDownLatch.await()
}

fun deferTest() {
    val defer = Observable.defer<String> {
        Observable.create {
            it.onNext("${System.currentTimeMillis()}")
        }
    }
    defer.subscribe {
        println(it)
    }
    Thread.sleep(20)
    defer.subscribe {
        println(it)
    }
    Thread.sleep(20)
    defer.subscribe {
        println(it)
    }
}

fun justTest() {
    val just = Observable.just("${System.currentTimeMillis()}")
    just.subscribe { println(it) }
    Thread.sleep(20)
    just.subscribe { println(it) }
    Thread.sleep(20)
    just.subscribe { println(it) }
}

fun fromTest() {
    val arr = arrayListOf(1, 2, 3, 4)
    val list = listOf(1, 2, 3, 4)
    val set = setOf(1, 2, 3, 4)
    Observable.fromIterable(set).subscribe {
        println(it)
    }
}

fun intervalTest() {
    val countDown = CountDownLatch(1)
    Observable.interval(0, 50, TimeUnit.MILLISECONDS)
        .subscribe {
            if (it > 20) {
                countDown.countDown()
                return@subscribe
            }
            println("Now is running number $it times")
        }
    countDown.await()
}

fun repeatTest() {
    Observable.just("111").repeat(10).subscribe {
        println(it)
    }
}

fun timerTest() {
    val countDown = CountDownLatch(1)
    Observable.timer(2, TimeUnit.SECONDS).subscribe {
        println("do after two seconds")
        countDown.countDown()
    }
    countDown.await()
}

fun bufferTest() {
    // 数量维度
//    val observable = Observable.range(0, 10).buffer(2)
    // 时间维度
    val countDownLatch = CountDownLatch(1)
    val observable = Observable.create<Long> { emitter ->
        Observable.interval(0, 333L, TimeUnit.MILLISECONDS).subscribe {
            emitter.onNext(it)
        }
    }.buffer(2, 1, TimeUnit.SECONDS)
    observable.subscribe {
        it.forEach { data ->
            print("subscribe[1] = $data,")
        }
        println()
    }
//    observable.subscribe {
//        it.forEach { data ->
//            print("subscribe[2] $data,")
//        }
//        println()
//    }
    countDownLatch.await()
}

fun flatMapTest() {
    // 类型转换
//    Observable.range(0, 10).flatMap<String> {
//        Observable.just("Flat Map $it")
//    }.subscribe {
//        println(it)
//    }
    // 类型转换，1 转 多，再推流到 subscribe
    Observable.range(0, 10).flatMapIterable {
        val list = mutableListOf<Int>()
        list.add(it)
        list.add(it)
        list
    }.subscribe {
        println(it)
    }
}

fun groupByTest() {
    Observable.range(0, 10).groupBy {
        it % 2
    }.subscribe({
        val key = it.key
        it.subscribe({
            println("key = ${key}, value = $it")
        }, {
            it.printStackTrace()
        })
    }, {
        it.printStackTrace()
    })
}

fun mapTest() {
    Observable.range(0, 10).map {
        "$it to a String"
    }.subscribe({
        println(it)
    }, {
        it.printStackTrace()
    })
}

sealed class Animal {
    class Dog : Animal() {
        override fun say() = println("I'm Dog, Wang~Wang!")
    }

    class Cat : Animal() {
        override fun say() = println("I'm Cat, Mio~Mio!")
    }

    class Pig : Animal() {
        override fun say() = println("I'm Pig, On~On!")
    }

    abstract fun say()
}

fun castTest() {
    val animals = listOf(
            Animal.Dog(),
            Animal.Cat(),
            Animal.Pig(),
            Animal.Cat(),
            Animal.Cat(),
            Animal.Pig()
    )
    Observable.fromIterable(animals)
        .cast(Animal::class.java)
        .subscribe({
            it.say()
        }, {
            it.printStackTrace()
        })
}

fun scanTest() {
    Observable.range(0, 10).scan { t1: Int?, t2: Int? ->
        (t1 ?: 0) + (t2 ?: 0)
    }.subscribe({
        println("$it")
    }, {
        it.printStackTrace()
    })
}

fun windowTest() {
    Observable.range(0, 10)
        .window(3)
        .subscribe({
            println("Got a Observable: $it")
            it.subscribe({
                println("$it")
            }, {
                it.printStackTrace()
            })
        }, {
            it.printStackTrace()
        })
}

fun debounceTest() {
    val countDownLatch = CountDownLatch(1)
    Observable.interval(100, TimeUnit.MILLISECONDS)
        .throttleWithTimeout(100, TimeUnit.MILLISECONDS)
        .subscribe({
            println("$it")
        }, {
            it.printStackTrace()
        })
    countDownLatch.await()
}

fun publisherTest() {
    val countDownLatch = CountDownLatch(1)
    val publisher = PublishSubject.create<String>()
    publisher.subscribe({
        println(it)
    }, {
        it.printStackTrace()
    })
    Observable.interval(0, 333, TimeUnit.MILLISECONDS)
        .observeOn(Schedulers.newThread())
        .subscribe({
            publisher.onNext("${System.currentTimeMillis()}: $it")
        }, {
            it.printStackTrace()
        })
    countDownLatch.await()
}

fun techTest() {
    val o = Observable.create<Long> {
        it.onNext(System.currentTimeMillis())
    }
    o.subscribe({
        println(it)
    }, {
        it.printStackTrace()
    })
    o.subscribe({
        println(it)
    }, {
        it.printStackTrace()
    })
}

fun publishTest() {
    val countDownLatch: CountDownLatch = CountDownLatch(1)
    val publisher: PublishProcessor<Long> = PublishProcessor.create()
    Observable.interval(0, 333, TimeUnit.MILLISECONDS)
        .observeOn(Schedulers.newThread())
        .subscribe({
            if (it % 10 < 5)
                publisher.onNext(System.currentTimeMillis())
        }, {
            it.printStackTrace()
        })
    publisher.buffer(500, TimeUnit.MILLISECONDS)
        .observeOn(Schedulers.newThread())
        .subscribe({
            println("Time:${it}")
        }, {
            it.printStackTrace()
        })
    countDownLatch.await()
}

class Monitor

class MainBoard

class Cpu

data class Phone(
        val monitor: Monitor,
        val mainBoard: MainBoard,
        val cpu: Cpu
)

fun zipTest() {
    // 生产线 A
    val aObservable = Observable.create<Monitor> {
        for (i in 0..10) {
            // 模拟生产时间
            Thread.sleep(3000)
            it.onNext(Monitor())
        }
    }
    // 生产线 B
    val bObservable = Observable.create<MainBoard> {
        for (i in 0..10) {
            // 模拟生产时间
            Thread.sleep(3000)
            it.onNext(MainBoard())
        }
    }
    // 生产线 C
    val cObservable = Observable.create<Cpu> {
        for (i in 0..10) {
            // 模拟生产时间
            Thread.sleep(6000)
            it.onNext(Cpu())
        }
    }

    Observable.zip(
        aObservable,
        bObservable,
        cObservable,
        Function3<Monitor, MainBoard, Cpu, Phone> { t1, t2, t3 ->
            Phone(t1, t2, t3)
        })
        .subscribe({
            println(it)
        }, {
            it.printStackTrace()
        }, {
            println("监听数据流完毕")
        })
}