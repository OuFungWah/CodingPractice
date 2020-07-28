package coding.practice.algorithm.string

import java.util.*

fun main() {
    println(Question36().isPalindrome("A man, a plan, a canal: Panama"))
    println(Question36().isPalindrome("."))
    println(Question36().isPalindrome("a."))
    println(Question36().isPalindrome("aa"))
    println(Question36().isPalindrome("0P"))
    println(Question36().isPalindrome(".,"))
}

class Question36 {

    /**
     * 思路：去除无用字符，然后用栈操作
     */
    fun isPalindrome2(s: String): Boolean {
        val range = '0'.toInt()..'z'.toInt()
        val realContent = s.filter {
            it.toInt() in range && it != ':'
        }.toLowerCase()
        println(realContent)
        val stack = Stack<Char>()
        val midPoint = realContent.length / 2
        val isEven = realContent.length % 2 == 0
        for (index in 0 until realContent.length) {
            val curChar = realContent[index]
            when (index) {
                in (0 until midPoint) -> {
                    stack.push(curChar)
                }
                in (midPoint until realContent.length) -> {
                    if (index != midPoint || isEven) {
                        if (stack.lastElement() == curChar) {
                            stack.pop()
                        }
                    }
                }
            }
        }
        return stack.size == 0
    }

    private val charRangeH = 'A'.toInt()..'Z'.toInt()
    private val charRangeL = 'a'.toInt()..'z'.toInt()
    private val numRange = '0'.toInt()..'9'.toInt()

    private fun Char.isVail(): Boolean = this.toInt() in charRangeH || this.toInt() in charRangeL || this.toInt() in numRange

    /**
     * 思路：
     * 1. 收尾同时开始遍历，只看有效字符
     * 2. 若有效字符相等，继续遍历。不相等则不是回文串
     * 3. 直到头尾两个游标相交（i > j）
     *
     * 注意：
     * 1. 游标相等也要判断，因为一个字符也是回文
     * 2. 注意一个有效字符都没有发现时判断为空串，空串在题目中也算回文
     *
     * 耗时 208ms
     */
    fun isPalindrome(s: String): Boolean {
        if (s.length <= 1) return true
        println(s)
        var i = 0
        var j = s.length - 1
        var result = false
        while (i < j) {
            while (!s[i].toLowerCase().isVail() && i + 1 < s.length) i++
            while (!s[j].toLowerCase().isVail() && j - 1 >= 0) j--
            if (i == s.length - 1 && j == 0) return true
            println("content[$i] ${s[i]} == content[$j] ${s[j]} : ${(s[i] == s[j])} ")
            if (i > j) {
                break
            }
            result = s[i].toLowerCase() == s[j].toLowerCase()
            if (!result) break
            i++
            j--
        }
        return result
    }

    /**
     * 耗时 240 ms
     */
    fun isPalindrome3(s: String): Boolean {
        val content = s.toLowerCase().filter { it.isVail() }
        if (content.length <= 1) return true
        println(content)
        var i = 0
        var j = content.length - 1
        var result = false
        while (i < j) {

            if (i == content.length - 1 && j == 0) return true
            if (i > j) {
                break
            }
            result = content[i] == content[j]
            if (!result) break
            i++
            j--
        }
        return result
    }

    /**
     * 耗时 264 ms
     */
    fun isPalindrome4(s: String): Boolean {
        val content = s.filter { it.isVail() }.toLowerCase()
        if (content.length <= 1) return true
        println(content)
        var i = 0
        var j = content.length - 1
        var result = false
        while (i < j) {

            if (i == content.length - 1 && j == 0) return true
            if (i > j) {
                break
            }
            result = content[i] == content[j]
            if (!result) break
            i++
            j--
        }
        return result
    }

}