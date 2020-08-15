package testla

import game.IGame
import java.util.*

fun main(){
    val loader = ServiceLoader.load(IGame::class.java)
    loader.forEach {
        println(it)
        it.run()
    }
}