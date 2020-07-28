package programming.question

/**
 *
 * a:{
 *  b:{
 *    c:{
 *      d:"answer"
 *    }
 *  }
 * }
 *
 *
 *
 */
fun main() {
    val a = A()
    val result = findVal(a, "b.c.d", "default")
    println(result)
}

class A {
    @JvmField
    val b: B = B()
}

class B {
    @JvmField
    val c: C = C()
}

class C {
    @JvmField
    val d = "answer"
}

fun findVal(obj: Any, path: String, default: String): String {
    val list: List<String> = path.split(".")
    var nowObj = obj
    list.forEachIndexed { index, memberName ->
        val member = findMember(nowObj, memberName) ?: return default
        if (index == list.size - 1) return member.toString()
        nowObj = member
    }
    return default
}

fun findMember(obj: Any, valName: String): Any? {
    val clazz = obj::class.java
    val field = clazz.fields.firstOrNull {
        it.name == valName
    } ?: return null
    return field.get(obj)
}

/**
 * 用上一次的执行结果作为下一次的执行输入
 */
fun test(input: String): String{
    val output = input
    return test(output)
}

// 方法栈