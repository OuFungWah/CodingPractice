package coding.practice.test

fun main(){
//    println(Integer.toBinaryString(8))
    val testing = Testing()
    Thread{
        testing.method("A")
    }.start()

    Thread{
        testing.method("B")
    }.start()
}

class Testing{

    val obj = Any()

    fun method(name : String){
        println("$name sleep start")
        synchronized(obj){
            var time = 0
            while (time < 3){
                println("$name using")
                Thread.sleep(1000)
                time++
            }
        }
        println("$name sleep end")
    }
}

class GitRebaseTest {

    // 在 Home  分支做修改
    fun test() {
        // 在 Master 分支中做修改
        // 1
        // 2
        // 3
    }

}