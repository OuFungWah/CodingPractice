package coding.practice.algorithm.link

class Question44 {
    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

    /**
     * 思路：归并排序
     */
    fun mergeTwoLists2(l1: ListNode?, l2: ListNode?): ListNode? {
        val god = ListNode(0)
        var cur: ListNode? = god
        var first: ListNode? = l1
        var second: ListNode? = l2
        while (first != null || second != null) {
            var temp: ListNode? = null
            when {
                first != null && second != null -> {
                    when {
                        first.`val` > second.`val` -> {
                            temp = second
                            second = second?.next
                        }
                        else -> {
                            temp = first
                            first = first?.next

                        }
                    }
                }
                first != null -> {
                    temp = first
                    first = first?.next
                }
                second != null -> {
                    temp = second
                    second = second?.next
                }
                else -> null
            }
            cur?.next = temp
            cur = cur?.next
        }
        return god.next
    }

    /**
     * 思路：插入排序
     *
     * 执行用时： 196 ms , 在所有 Kotlin 提交中击败了 84.42% 的用户
     * 内存消耗： 33.8 MB , 在所有 Kotlin 提交中击败了 33.33% 的用户
     */
    fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        val god = ListNode(0)
        god.next = l1
        var cur: ListNode? = god
        var second = l2
        while (second != null) {
            val nextSecond = second.next
            val curNext = cur?.next
            if (second.`val` < cur?.next?.`val` ?: Int.MAX_VALUE) {
                second?.next = curNext
                cur?.next = second
                cur = cur?.next
                second = nextSecond
            } else {
                cur = curNext
            }
        }
        return god.next
    }
}