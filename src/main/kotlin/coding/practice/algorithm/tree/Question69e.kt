package coding.practice.algorithm.tree

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