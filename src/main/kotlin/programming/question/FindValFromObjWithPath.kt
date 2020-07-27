package programming.question


fun main() {
    val a = A()
    println(findVal(a, "b.c.d", "default"))
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
    val list = path.split(".")
    var nowObj = obj
    list.forEachIndexed { index, memberName ->
        val member = findMember(nowObj, memberName) ?: return default
        if (index == list.size - 1) return member.toString()
        nowObj = member
    }
    return default
}

fun findMember(obj: Any, valName: String): Any? {
    println("----------$valName---------")
    val clazz = obj::class.java
    println("clazzName = $clazz")
    val field = clazz.fields.firstOrNull {
        println("memberName = ${it.name}")
        it.name == valName
    } ?: return null
    println("fiedl = $field")
    println("member = ${field.get(obj)}")
    return field.get(obj)
}