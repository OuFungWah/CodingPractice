package coding.practice.algorithm.link

/**
给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。

示例：

给定一个链表: 1->2->3->4->5, 和 n = 2.

当删除了倒数第二个节点后，链表变为 1->2->3->5.
说明：

给定的 n 保证是有效的。

进阶：

你能尝试使用一趟扫描实现吗？


Kotlin

Example:
var li = ListNode(5)
var v = li.`val`
Definition for singly-linked list.
class ListNode(var `val`: Int) {
var next: ListNode? = null
}

@see <a href="https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xn2925/">链接</a>
 */
class Question42 {

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

    /**
     * 思路：利用 List 可以用 index 定位的特性
     */
    fun removeNthFromEnd2(head: ListNode?, n: Int): ListNode? {
        val tempList = mutableListOf<ListNode?>()
        val leader: ListNode = ListNode(0)
        leader.next = head
        var cur: ListNode? = leader
        while (cur != null) {
            tempList.add(cur)
            cur = cur.next
        }
        val total = tempList.size
        tempList[total - n - 1]?.next = tempList[total - n]?.next
        return leader.next
    }

    /**
     * 思路：
     * 1、双指针保持距离 (n + 1) 遍历
     * 2、等第一个指针触底以后，第二个指针就是正确的位置了
     */
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        val leader: ListNode = ListNode(0)
        leader.next = head
        var first: ListNode? = leader
        var second: ListNode? = leader
        var count = 0
        while (first != null) {
            first = first.next
            count++
            // 为什么 = n + 1 也不行？
            // 因为 n + 1 的时候 First 指针 才刚刚好到目标位置，这个时候 second 开始行动的话会直接落在要被删除的 item 上。
            if (count <= n + 1) {
                continue
            }
            second = second?.next
        }
        second?.next = second?.next?.next
        return leader.next
    }

}