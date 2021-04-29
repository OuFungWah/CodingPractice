package coding.practice.test

import io.reactivex.rxjava3.core.Completable
import java.util.concurrent.CountDownLatch

fun main() {
    val list = mutableListOf<String>("H", "E", "L", "L", "O")
    list.forEach {
        list.add(it)
    }
}