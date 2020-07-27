package coding.practice.algorithm.string

import java.util.*

/*
给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。



示例：

s = "leetcode"
返回 0

s = "loveleetcode"
返回 2


提示：你可以假定该字符串只包含小写字母。
* */


/**
 * @see <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/5/strings/34/">链接</a>
 */
fun main() {
    println(Solution().firstUniqChar2("leetcode"))
}

class Solution {

    /**
     * 思路是记录每个字符出现的次数，然后在再找第一个出现次数为 1 的字符
     *
     * 耗时 280～260ms 之间
     */
    fun firstUniqChar(s: String): Int {
        val arr = Array(26) { 0 }
        val zeroPoint = 'a'.toInt()
        s.forEach { word: Char ->
            arr[word.toInt() - zeroPoint] += 1
        }
        println(arr.toString())
        var result = -1
        s.forEachIndexed { index, word ->
            if (arr[(word.toInt() - zeroPoint)] == 1) {
                result = index
                return result
            }
        }
        return result
    }

    /**
     * 思路与上同
     *
     * 耗时 380 以上
     */
    fun firstUniqChar2(s: String): Int {
        val map = hashMapOf<Char, Int>()
        s.forEach {
            map[it] = map[it]?.plus(1) ?: 1
        }
        s.forEach {
            if (map[it] == 1) return s.indexOf(it)
        }
        return -1
    }

    //TODO ofh 确认 map 和 数组之间的速度
}