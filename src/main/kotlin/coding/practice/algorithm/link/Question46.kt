package coding.practice.algorithm.link

/**
给定一个链表，判断链表中是否有环。

为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。

 

示例 1：

输入：head = [3,2,0,-4], pos = 1
输出：true
解释：链表中有一个环，其尾部连接到第二个节点。


示例 2：

输入：head = [1,2], pos = 0
输出：true
解释：链表中有一个环，其尾部连接到第一个节点。


示例 3：

输入：head = [1], pos = -1
输出：false
解释：链表中没有环。


 

进阶：

你能用 O(1)（即，常量）内存解决此问题吗？


@see <a href="https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnwzei/">链接</a>
 *
 */

class Question46 {
    /**
     * 思路：
     * 1、一边遍历一边拆链并加入遍历标识
     * 2、遍历到遍历标识时表明已经闭环了。
     *
     * 有个问题就是：如果这么做会完全改动链表，且不能记载位置
     */
    fun hasCycle(head: ListNode?): Boolean {
        val temp = ListNode(0)
        var cur = head
        while (cur != null) {
            if (cur.next == temp) {
                return true
            } else if (cur.next != null) {
                val next = cur?.next
                cur?.next = temp
                cur = next
            } else {
                break
            }
        }
        return false
    }
}
