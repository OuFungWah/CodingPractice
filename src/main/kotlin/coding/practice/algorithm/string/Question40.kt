package coding.practice.algorithm.string

fun main() {
    println(Question40().longestCommonPrefix(arrayOf("flex", "flash")))
}

/**
最长公共前缀
编写一个函数来查找字符串数组中的最长公共前缀。

如果不存在公共前缀，返回空字符串 ""。

示例 1:

输入: ["flower","flow","flight"]
输出: "fl"

示例 2:

输入: ["dog","racecar","car"]
输出: ""
解释: 输入不存在公共前缀。
说明:

所有输入只包含小写字母 a-z 。

 */
class Question40 {

    /**
     * 核心思想：
     * 1、遍历各个字符串
     * 2、按次序逐个比较字符
     *
     * 执行用时： 220 ms , 在所有 Kotlin 提交中击败了 52.17% 的用户
     * 内存消耗： 34.1 MB , 在所有 Kotlin 提交中击败了 87.50% 的用户
     *
     * @see <a href="https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnmav1/">链接</a>
     */
    fun longestCommonPrefix(strs: Array<String>): String {
        val result = StringBuffer()
        var index = 0
        var flag = true
        while (flag) {
            var cur: Char? = null
            for (str in strs) {
                println("$str index = $index")
                if (str.length > index && (cur == null || str[index] == cur)) {
                    cur = str[index]
                } else {
                    cur = null
                    break
                }
            }
            if (cur == null) {
                flag = false
            } else {
                result.append(cur)
            }
            index++
        }
        return result.toString()
    }

}