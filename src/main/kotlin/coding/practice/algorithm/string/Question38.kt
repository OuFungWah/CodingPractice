package coding.practice.algorithm.string

fun main() {
    val haystack = "mississippi"
    val needle = "issippi"
//    val haystack = "aabaaabaaac"
//    val needle = "aabaaac"
//    val haystack = "ababaabbbbababbaabaaabaabbaaaabbabaabbbbbbabbaabbabbbabbbbbaaabaababbbaabbbabbbaabbbbaaabbababbabbbabaaabbaabbabababbbaaaaaaababbabaababaabbbbaaabbbabb"
//    val needle = "abbabbbabaa"
    println(haystack.toList())
    println(Array(haystack.length) { it }.toList())
    println(needle.toList())
    println(Question38().tableBuilding(needle).toList())
    println(Question38().strStr(haystack, needle))
}

/**
实现 strStr() 函数。

给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。

示例 1:

输入: haystack = "hello", needle = "ll"
输出: 2
示例 2:

输入: haystack = "aaaaa", needle = "bba"
输出: -1
说明:

当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。

对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与C语言的 strstr() 以及 Java的 indexOf() 定义相符。
 */
class Question38 {
    /**
     * 耗时：16% ～ 0%
     *
     * 思想：
     * 1、逐个遍历主串
     * 2、逐个遍历子串
     * 3、如果其中一个字符不匹配，主串游标位置不变，挪动子串游标到 table[子串游标] 处，以复用前面的匹配结果。
     * 4、如果 table[子串游标] 为 -1，主串和子串的游标一起继续往后挪。
     *
     * TODO ofh 很奇怪，为什么明明是 KMP 算法，但是效率却比很多其他算法慢呢
     */
    fun strStr(haystack: String, needle: String): Int {
        val table = tableBuilding(needle)
        var i = 0
        var j = 0
        while (i < haystack.length && j < needle.length) {
            when {
                haystack[i] == needle[j] -> {
                    i++
                    j++
                }
                else -> {
                    if (table[j] == -1) {
                        i++
                    }
                    j = table[j].takeIf { it >= 0 } ?: 0
                }
            }
        }
        return if (j >= needle.length) {
            i - needle.length
        } else {
            -1
        }
    }

    /**
     * 建表的思想是:
     * 当前字符是否相等决定了下一个字符的 offset（偏移量）
     * 也就是利用上一次的结果
     *
     * 不相等时，需要判断区间
     *
     */
    fun tableBuilding(pattern: String): Array<Int> {
        val table = Array(pattern.length) { if (it == 0) -1 else 0 }
        for (index in 2 until pattern.length) {
            if (pattern[index - 1] == pattern[table[index - 1]]) {
                table[index] = table[index - 1] + 1
            } else {
                val count = table[index - 1]
                if (pattern.substring(0 until count) == pattern.substring(index - count until index)) table[index] = table[index - 1]
            }
        }
        return table
    }
}