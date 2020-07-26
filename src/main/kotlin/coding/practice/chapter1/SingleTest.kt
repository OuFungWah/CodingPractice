package coding.practice.chapter1

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.core.SingleOnSubscribe
import io.reactivex.rxjava3.disposables.Disposable

fun main(){

}

fun singleCreateTest(){
    Single.create(object : SingleOnSubscribe<String> {
        override fun subscribe(emitter: SingleEmitter<String>?) {
            // use emitter send a data to observer
            emitter?.onSuccess("Hello World")
        }
    }).subscribe(object: SingleObserver<String>{
        override fun onSuccess(t: String?) {
            // do something while receive data
            println(t)
        }

        override fun onSubscribe(d: Disposable?) {
            // do something while onSubscribe
        }

        override fun onError(e: Throwable?) {
            // do something while error was throw
        }
    })

    Single.create<String> {
        // use emitter send a data to observer
        it.onSuccess("Hello World")
    }.subscribe({
        // do something while receive data
        println(it)
    },{
        // do something while error was throw
    })
}