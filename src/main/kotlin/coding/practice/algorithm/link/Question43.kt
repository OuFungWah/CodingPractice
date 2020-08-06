package coding.practice.algorithm.link

/**
反转链表
反转一个单链表。

示例:

输入: 1->2->3->4->5->NULL
输出: 5->4->3->2->1->NULL
进阶:
你可以迭代或递归地反转链表。你能否用两种方法解决这道题？


 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }

 */

/**
 * @see <a href="https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnnhm6/">链接</a>
 */
class Question43 {
    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

    /**
     * 思想：
     * 1、遍历一次链表
     * 2、当前元素的 next 先拿出来存作 Temp
     * 3、当前元素的 next 接上 god 元素的 next，以保证当前插入到了最前位置
     * 4、god 的 next 接上当前元素
     * 5、当前元素变成 Temp，继续遍历剩余部分
     */
    fun reverseList(head: ListNode?): ListNode? {
        val god: ListNode = ListNode(0)
        var cur: ListNode? = head
        while (cur != null) {
            val temp = cur.next
            cur.next = god.next
            god.next = cur
            cur = temp
        }
        return god.next
    }

    /**
     * 思想：利用递归
     */
    fun reverseList2(head: ListNode?): ListNode? {
        return null
    }
}