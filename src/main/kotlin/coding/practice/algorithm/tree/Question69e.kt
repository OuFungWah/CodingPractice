package coding.practice.algorithm.tree

/**
二叉树的最大深度
给定一个二叉树，找出其最大深度。

二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。

说明: 叶子节点是指没有子节点的节点。

示例：
给定二叉树 [3,9,20,null,null,15,7]，

@see <a href="https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnd69e/">链接</a>
 */
class Question69e {

    /**
     * 思路：
     * 1、利用递归的回溯性
     * 2、逐个分支遍历
     */
    fun maxDepth(root: TreeNode?): Int {
        if (root == null) return 0
        val leftDepth = 1 + maxDepth(root?.left)
        val rightDepth = 1 + maxDepth(root?.right)
        return Math.max(leftDepth, rightDepth)
    }

    /**
     * 思路：
     * 1、利用栈的数据结构
     */
    fun maxDepth2(root: TreeNode?): Int {
        return 0
    }
}