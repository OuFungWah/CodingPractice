package coding.practice.algorithm.string
/*
给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。

示例 1:

输入: 123
输出: 321
 示例 2:

输入: -123
输出: -321
示例 3:

输入: 120
输出: 21
注意:

假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
*/

/**
 *
 * 解题思路：
 *
 * 反向遍历、Int 值的边界情况
 *
 * @see <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/5/strings/33/">链接</a>
 */
class Question33 {
    fun reverse(x: Int): Int {
        val flag = if (x < 0) -1 else 1
        val numStr = "${x * flag}"
        var result = ""
        for (c in numStr) {
            result = c + result
        }
        return try {
            result.toInt() * flag
        } catch (e: Exception) {
            0
        }
    }
}