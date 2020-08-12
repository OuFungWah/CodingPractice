package coding.practice.algorithm.tree

import java.util.*
import java.util.concurrent.LinkedBlockingDeque

fun main() {
    val legalTree = TreeNode(1).apply {
        left = TreeNode(2).apply {
            left = TreeNode(3)
            right = TreeNode(4)
        }
        right = TreeNode(2).apply {
            left = TreeNode(4)
            right = TreeNode(3)
        }
    }
    println(QuestionBFS().levelOrder(legalTree))
}

/**
二叉树的层序遍历
给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。

 

示例：
二叉树：[3,9,20,null,null,15,7],

3
/ \
9  20
/  \
15   7
返回其层次遍历结果：

[
[3],
[9,20],
[15,7]
]

@see <a href="https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnldjj/">链接</a>
 */
class QuestionBFS {

    /**
     * 思路：用队列记录一个层级的所有节点
     * 1、将 root 塞入 queue
     * 2、遍历 Queue，将 queue 中所有节点的 左右子节点都存放到一个新的 queue 中（水平铺开）
     */
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        if(root == null) return emptyList()
        var queue: Queue<TreeNode> = LinkedBlockingDeque<TreeNode>()
        val result = mutableListOf<List<Int>>()
        queue.offer(root)
        while (queue.isNotEmpty()) {
            val newQueue = LinkedBlockingDeque<TreeNode>()
            val row = mutableListOf<Int>()
            while (queue.isNotEmpty()) {
                val node = queue.poll()
                if (node.left != null) newQueue.add(node.left!!)
                if (node.right != null) newQueue.add(node.right!!)
                row.add(node.`val`)
            }
            result.add(row)
            queue = newQueue
        }
        return result
    }
}