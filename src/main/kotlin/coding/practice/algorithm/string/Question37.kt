package coding.practice.algorithm.string

import java.lang.Exception

/**
请你来实现一个 atoi 函数，使其能将字符串转换成整数。

首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。接下来的转化规则如下：

如果第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字字符组合起来，形成一个有符号整数。
假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成一个整数。
该字符串在有效的整数部分之后也可能会存在多余的字符，那么这些字符可以被忽略，它们对函数不应该造成影响。
注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换，即无法进行有效转换。

在任何情况下，若函数不能进行有效的转换时，请返回 0 。

提示：

本题中的空白字符只包括空格字符 ' ' 。
假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，请返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。


示例 1:

输入: "42"
输出: 42
示例 2:

输入: "   -42"
输出: -42
解释: 第一个非空白字符为 '-', 它是一个负号。
我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
示例 3:

输入: "4193 with words"
输出: 4193
解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
示例 4:

输入: "words and 987"
输出: 0
解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
因此无法执行有效的转换。
示例 5:

输入: "-91283472332"
输出: -2147483648
解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
因此返回 INT_MIN (−231) 。
 */
fun main() {
//    println(Question37().myAtoi("   -42"))
    println(Question37().myAtoi("   +-42"))
//    println(Question37().myAtoi("XX1245"))
//    println(Question37().myAtoi(".1"))
//    println(Question37().myAtoi("  0000000000012345678"))
//    println(Question37().myAtoi("42"))
}

/**
 * @see <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/5/strings/37/">链接</a>
 */
class Question37 {

    companion object {
        private val numRange = '0'.toInt()..'9'.toInt()
        private val operator = listOf('+', '-')
    }

    enum class STATE {
        DEFAULT,
        OPERATOR,
        ZERO,
        NUMBER,
        CHARACTER,
        END
    }

    private fun Char.isVail(): Boolean = this.toInt() in numRange
    private fun Char.isZero(): Boolean = this == '0'
    private fun Char.isOperator(): Boolean = operator.contains(this)
    private fun Char.isCharacter(): Boolean = this in 'a'..'z' || this in 'A'..'Z'

    /**
     * 思路：
     * 1. 不定状态机，解决各种不同情况（例如：0开头、字母开头、空格等等）
     * 2. Int 值边界问题
     *
     * 耗时：前 50%～30%
     */
    fun myAtoi(str: String): Int {
        var state = STATE.DEFAULT
        var isMinus = false
        var targetNum = StringBuffer()
        for (index in 0..str.length - 1) {
            val cur = str[index]
            when (state) {
                STATE.DEFAULT -> {
                    state = when {
                        cur.isOperator() -> {
                            isMinus = when (cur) {
                                '+' -> false
                                else -> true
                            }
                            STATE.OPERATOR
                        }
                        cur.isZero() -> {
                            STATE.ZERO
                        }
                        cur.isVail() -> {
                            targetNum.append(cur)
                            STATE.NUMBER
                        }
                        cur == ' ' -> STATE.DEFAULT
                        else -> STATE.CHARACTER
                    }
                }
                STATE.ZERO -> {
                    state = when {
                        cur.isZero() -> STATE.ZERO
                        cur.isVail() -> {
                            targetNum.append(cur)
                            STATE.NUMBER
                        }
                        else -> STATE.CHARACTER
                    }
                }
                STATE.OPERATOR -> {
                    state = when {
                        cur.isVail() -> {
                            targetNum.append(cur)
                            STATE.NUMBER
                        }
                        else -> {
                            STATE.CHARACTER
                        }
                    }
                }
                STATE.NUMBER -> {
                    if (cur.isVail())
                        targetNum.append(cur)
                    else
                        state = STATE.END
                }
            }
            if (state == STATE.END || state == STATE.CHARACTER) break
        }
        println("state = $state; ${if (isMinus) "-" else ""}$targetNum")
        return when (state) {
            STATE.NUMBER,
            STATE.END -> try {
                targetNum.toString().toInt() * if (isMinus) -1 else 1
            } catch (e: Exception) {
                if (isMinus) Int.MIN_VALUE else Int.MAX_VALUE
            }
            else -> 0
        }
    }
}