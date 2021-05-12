import java.util.*

/**
 *
 * 给你一个由 不同 整数组成的数组 nums ，和一个目标整数 target 。请你从 nums 中找出并返回总和为 target 的元素组合的个数。
 *
 * 题目数据保证答案符合 32 位整数范围。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,3], target = 4
 * 输出：7
 * 解释：
 * 所有可能的组合为：
 * (1, 1, 1, 1)
 * (1, 1, 2)
 * (1, 2, 1)
 * (1, 3)
 * (2, 1, 1)
 * (2, 2)
 * (3, 1)
 * 请注意，顺序不同的序列被视作不同的组合。
 * 示例 2：
 *
 * 输入：nums = [9], target = 3
 * 输出：0
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 1000
 * nums 中的所有元素 互不相同
 * 1 <= target <= 1000
 *  
 *
 * 进阶：如果给定的数组中含有负数会发生什么？问题会产生何种变化？如果允许负数出现，需要向题目中添加哪些限制条件？
 * https://leetcode-cn.com/problems/combination-sum-iv/
 * */
// 记忆化、深度优先算法
class CombinationSumIv {

    private val memoryResult = hashMapOf<Int, Int>()

    fun memoryDeepFirstSearch(nums: IntArray, target: Int): Int {
        var targetResult = 0
        if (target == 0) return 1
        if (target < 0) return 0
        if (memoryResult[target] != null) return memoryResult[target]!!
        for (num in nums) {
            targetResult += memoryDeepFirstSearch(nums, target - num)
        }
        memoryResult[target] = targetResult
        return targetResult
    }

    fun combinationSum4(nums: IntArray, target: Int): Int {
        return memoryDeepFirstSearch(nums, target)
    }
}

// 该方法效率极低
//class CombinationSumIv {
//
//    private val stack = Stack<Int>()
//
//    fun combinationSum4(nums: IntArray, target: Int): Int {
//        nums.sort()
//        var result = 0
//        stack.push(0)
//        var back = false
//        loop@ while (stack.isNotEmpty()) {
//            if (stack.peek() < nums.size) {
//                val sum = stack.sumBy { nums[it] }
//                when {
//                    sum > target -> {
//                        stack.pop()
//                        if (stack.isEmpty()) break@loop
//                        back = true
//                    }
//                    sum == target -> {
//                        result++
//                        println("Answer: " + stack.map { nums[it] })
//                        stack.pop()
//                        if (stack.isEmpty()) break@loop
//                        back = true
//                    }
//                    sum < target -> {
//                        if (back) {
//                            stack.push(stack.pop() + 1)
//                        } else {
//                            stack.push(0)
//                        }
//                        back = false
//                    }
//                }
//            } else {
//                stack.pop()
//            }
//            println(stack)
//        }
//        return result
//    }
//
//}

fun main() {
    val c = CombinationSumIv()
    println(c.combinationSum4(IntArray(2) { it + 1 }, 10))
}