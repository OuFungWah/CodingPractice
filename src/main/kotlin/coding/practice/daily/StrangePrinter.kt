package coding.practice.daily

class StrangePrinter {
    fun strangePrinter(s: String): Int {
        val set = hashSetOf<Char>()
        s.forEach { char ->
            set.add(char)
        }
        return set.size
    }
}

fun main(){
    var i = 0
    var j = 0
    if((++i > 0) || (++j > 0)){
        println("$i, $j")
    }
}