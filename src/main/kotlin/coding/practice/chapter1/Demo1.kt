package coding.practice.chapter1

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


fun main() {
    testCompletableMerge()
}

fun test() {
    val l = 747293161564557L
    val test = Base64.getEncoder().encode(twiceLongToBytes(l))
    val testStr = String(test)
    println("maxtest base64:$testStr")
    val originValue = "true:1000:1:6813970429334525704"
//    println(AesCryptUtil.encrypt(testStr, originValue))
    println(originValue.encrypt(testStr))
}


fun String.encrypt(password: String): String {
    val secretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
    val iv = ByteArray(password.toCharArray().size)
    val charArray = password.toCharArray()
    for (i in 0 until charArray.size) {
        iv[i] = charArray[i].toByte()
    }
    val ivParameterSpec = IvParameterSpec(iv)

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)

    val encryptedValue = cipher.doFinal(this.toByteArray())
    return Base64.getEncoder().encodeToString(encryptedValue)
}

//fun String.decrypt(password: String): String {
//    val secretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
//    val iv = ByteArray(16)
//    val charArray = password.toCharArray()
//    for (i in 0 until charArray.size){
//        iv[i] = charArray[i].toByte()
//    }
//    val ivParameterSpec = IvParameterSpec(iv)
//
//    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
//    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
//
//    val decryptedByteValue = cipher.doFinal(Base64.decode(this, Base64.DEFAULT))
//    return String(decryptedByteValue)
//}

fun twiceLongToBytes(number: Long): ByteArray? {
    val buffer: ByteBuffer = ByteBuffer.allocate(16)
    buffer.putLong(number)
    buffer.putLong(number)
    return buffer.array()
}

// -89 补码 167
// -88 补码 168

// 服务端 [0 2 167 168 191 112 41 141]
fun Long.longToBytes(): ByteArray? {
    val buffer: ByteBuffer = ByteBuffer.allocate(java.lang.Long.BYTES)
    buffer.putLong(this)
    buffer.order(ByteOrder.BIG_ENDIAN)
    val arr = UByteArray(java.lang.Long.BYTES)
    buffer.array().forEachIndexed { index, byte ->
        arr[index] = byte.toUByte()
    }
    arr.toByteArray().forEach {
        println("Ubyte: $it")
    }
    return arr.toByteArray()
}

fun ByteArray.byteArrToString(): String {
    val buffer: StringBuffer = StringBuffer()
    this.forEach {
        //        println(it)
        buffer.append(it)
    }
    return buffer.toString()
}

fun ByteArray.bytesToLong(): Long {
    val buffer: ByteBuffer = ByteBuffer.allocate(java.lang.Long.BYTES)
    buffer.put(this)
    buffer.flip() //need flip
    return buffer.getLong()
}

fun testNormal() {
    Observable.create<String>(ObservableOnSubscribe {
        for (i in 0..10) {
//            Thread.sleep(1000L)
            it.onNext("$i")
        }
    }).observeOn(Schedulers.io()).subscribe({
        println("Observable on Next $it")
    }, {
        println("Observable on Error")
        it.printStackTrace()
    }, {
        println("Observable on Complete")
    })
}

fun testSingle() {
    Single.create<String> {
        for (i in 0..10) {
//            Thread.sleep(1000L)
            it.onSuccess("$i")
        }
    }.subscribe({
        println("Single on success $it")
    }, {
        println("Single on error")
    })
}

fun testComplete() {
    Completable.create {
        //        Thread.sleep(1000L)
        it.onComplete()
    }.subscribe({
        println("Completable on success")
    }, {
        println("Completable on error")
    })
}

fun testRange() {
    Observable.range(0, 10).subscribe {
        println("$it")
    }
}

fun testDefer() {
    val defer = Observable.defer {
        Observable.create<Long> {
            it.onNext(System.currentTimeMillis())
        }
    }
    defer.subscribe {
        println("defer subscribe1 = ${it}")
    }

    defer.subscribe {
        println("defer subscribe2 = ${it}")
    }
}

fun testJust() {
    val just = Observable.just(System.currentTimeMillis())

    just.subscribe {
        println("defer just1 = ${it}")
    }
    just.subscribe {
        println("defer just2 = ${it}")
    }
}

fun testInterval() {
    val countDownLatch = CountDownLatch(1)
    val interval = Observable.interval(1, TimeUnit.SECONDS).observeOn(Schedulers.newThread())

    interval.subscribe({
        println("interval current = ${it}")
        if (it > 100) {
            countDownLatch.countDown()
        }
    }, {
        println("interval error")
    }, {
        println("interval complete")
    })

    // 防止主线程结束过快
    try {
        countDownLatch.await()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun testRepeat() {
    Observable
        .just("repeat test")
        .repeat(1000).subscribe {
            println(it)
        }
}

fun testTimer() {
    val countDownLatch = CountDownLatch(1)
    println("Waiting")
    Observable.timer(3, TimeUnit.SECONDS).subscribe {
        println("timer test")
        countDownLatch.countDown()
    }
    countDownLatch.await()
}

fun testBuzzer() {
    Observable.range(0, 16).buffer(4).subscribe {
        val result = StringBuffer("buffer onNext:")
        it.forEach {
            result.append(" $it ")
        }
        println(result)
    }
}

fun testBuzzer2() {
    val countDownLatch = CountDownLatch(1)
    println("Waiting")
    Observable.interval(1, TimeUnit.SECONDS).buffer(3, TimeUnit.SECONDS).subscribe {
        val result = StringBuffer("buffer2 onNext:")
        it.forEach {
            result.append(" $it ")
        }
        println(result)
        if (it.last() > 100) countDownLatch.countDown()
    }
    countDownLatch.await()
}

fun testMap() {
    // flatMap 将源 Observable<T> 转化成 Observable<R> 输出。次序不敏感
    Observable.range(0, 10).flatMap {
        Observable.create<String> {

        }
    }
    // concatMap 将源 Observable<T> 转化成 Observable<R> 输出。次序敏感
    Observable.range(0, 10).concatMap {
        Observable.create<String> {

        }
    }
//    Observable.range(0,10).flatMapIterable {
//
//    }
}

var b: CompletableEmitter? = null

fun testCompletableMerge() {
    val countDownLatch = CountDownLatch(1)
    Completable.mergeArray(
        Completable.create {
            Thread.sleep(1000)
            println("A finish")
            it.onComplete()
        }.subscribeOn(Schedulers.newThread()),
        Completable.create {
            b = it
        },
        Completable.create {
            Thread.sleep(3000)
            println("C finish")
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
    ).doOnComplete {
        println("All finish")
        countDownLatch.countDown()
    }.subscribe()
    Thread {
        Thread.sleep(2000)
        println("B finish")
        b?.onComplete()
    }.start()
    countDownLatch.await()
}

fun testSingleZip(){
    Single.create<String> {  }.zipWith<Boolean, String>(Single.create<Boolean>{}, BiFunction<String, Boolean, String>{ t1, t2 -> t1 })
}