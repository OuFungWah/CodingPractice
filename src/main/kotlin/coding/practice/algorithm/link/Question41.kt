package coding.practice.algorithm.link

/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

/**
 * @see <a href="https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnarn7/">链接</a>
 */
class Solution {
    val head = ListNode(5).apply {
        next = ListNode(4).apply {
            next = ListNode(1).apply {
                next = ListNode(9)
            }
        }
    }
    var li = ListNode(5)
    var v = li.`val`
    fun deleteNode(node: ListNode?) {
        var current: ListNode? = ListNode(0)
        current?.next = head
        do {
            if (current?.next?.`val` != null && current.next?.`val` == node?.`val`) {
                current?.next = current.next?.next
                break
            }
            current = current?.next
        } while (current?.next != null)
    }
}