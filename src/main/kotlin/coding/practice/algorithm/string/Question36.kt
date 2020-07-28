package coding.practice.algorithm.string

/**
给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。

示例 1:

输入: s = "anagram", t = "nagaram"
输出: true
示例 2:

输入: s = "rat", t = "car"
输出: false
说明:
你可以假设字符串只包含小写字母。

进阶:
如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？

 @see <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/5/strings/35/">链接</a>
 * */

fun main(){
    println(Question36().isAnagram("anagram", "nagaram"))
}

class Question36{

    /**
     * 思路：
     * 1. 长度
     * 2. 元素种类
     * 3. 元素各自数量，s 加，t 减
     * 4. 最后看各个元素的剩余数量
     */
    fun isAnagram(s: String, t: String): Boolean {
        if(s.length != t.length) return false
        val arr = Array(26) { 0 }
        val zeroPoint = 'a'.toInt()
        s.forEach { word: Char ->
            arr[word.toInt() - zeroPoint] += 1
        }
        t.forEach {  word: Char ->
            arr[word.toInt() - zeroPoint] -= 1
        }
        return !arr.any { it != 0 }
    }
}