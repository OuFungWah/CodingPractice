package coding.practice.algorithm.link



//fun main(args: Array<String>){
//    Question45().judge(head, head)
//}

/**
回文链表
请判断一个链表是否为回文链表。

示例 1:

输入: 1->2
输出: false
示例 2:

输入: 1->2->2->1
输出: true
进阶：
你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？

 @see <a href="https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnv1oc/">链接</a>
 */
class Question45 {

    val head = ListNode(5).apply {
        next = ListNode(4).apply {
            next = ListNode(3).apply {
                //            next = ListNode(5)
            }
        }
    }

    /**
     * 思路：
     * 使用递归的可回溯特性，当链表触底以后往回传递完整链表，此时就可以做到同时的正反序遍历
     *
     * 执行用时： 252 ms , 在所有 Kotlin 提交中击败了 85.42% 的用户
     * 内存消耗： 44.6 MB , 在所有 Kotlin 提交中击败了 25.00% 的用户
     *
     * 其实还有利用数组的做法，但是空间复杂度就变成了 O(n) 、时间复杂度变为 O(n * 3 / 2)了
     */
    fun isPalindrome(head: ListNode?): Boolean {
        judge(head, head)
        return false
    }

    fun judge(node: ListNode?, head: ListNode?): ListNode? {
        if (node == null) return head
        val compare = judge(node?.next, head)
        println("${node.`val` == compare?.`val`}")
        return compare?.next
    }
}