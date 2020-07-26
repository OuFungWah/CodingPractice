package coding.practice.chapter1

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers
import org.reactivestreams.Subscription
import java.lang.Exception
import java.util.concurrent.CountDownLatch

fun main() {
    flowableCreateTest()
}

fun flowableCreateTest() {
//    val countDownLatch = CountDownLatch(1)
    val source = Flowable.create(object : FlowableOnSubscribe<Long> {
        override fun subscribe(emitter: FlowableEmitter<Long>?) {
            try {
                while (true) {
                    Thread.sleep(300)
                    emitter?.onNext(System.currentTimeMillis())
                }
            } catch (e: Exception) {
                emitter?.onError(e)
            } finally {
                emitter?.onComplete()
            }
        }
    }, BackpressureStrategy.BUFFER)
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.newThread())
//    countDownLatch.await()
    val countDownLatch = CountDownLatch(1)
//    val source = Flowable.create(FlowableOnSubscribe<Long> { emitter ->
//        try {
//            while (true) {
//                Thread.sleep(300)
//                val time = System.currentTimeMillis()
//                println("posting data $time")
//                emitter?.onNext(time)
//            }
//        } catch (e: Exception) {
//            emitter?.onError(e)
//        } finally {
//            emitter?.onComplete()
//        }
//    }, BackpressureStrategy.BUFFER)
//        .subscribeOn(Schedulers.newThread())
//        .observeOn(Schedulers.newThread())
    source.subscribe(object : FlowableSubscriber<Long> {

        var d: Subscription? = null

        override fun onComplete() {
            // do something while onComplete
            println("onComplete")
        }

        override fun onSubscribe(d: Subscription?) {
            // do something while onSubscribe
            println("onSubscribe")
            this.d = d
            d?.request(1)
        }

        override fun onNext(t: Long?) {
            // do something while receive data
            try {
                Thread.sleep(200)
                println("now time is $t")
                d?.request(1)
            } catch (e: Exception) {
                onError(e)
            }
        }

        override fun onError(e: Throwable?) {
            // do something while error
            println("${e?.message}")
        }

    })
//    source.subscribe({
//        try {
//            Thread.sleep(200)
//            println("now time is $it")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    },{
//        it.printStackTrace()
//    })
    countDownLatch.await()
}