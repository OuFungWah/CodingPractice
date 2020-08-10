package coding.practice.algorithm.tree

/**
验证二叉搜索树
给定一个二叉树，判断其是否是一个有效的二叉搜索树。

假设一个二叉搜索树具有如下特征：

节点的左子树只包含小于当前节点的数。
节点的右子树只包含大于当前节点的数。
所有左子树和右子树自身必须也是二叉搜索树。
示例 1:

输入:
2
/ \
1   3
输出: true
示例 2:

输入:
5
/ \
1   4
     / \
    3   6
输出: false
解释: 输入为: [5,1,4,null,null,3,6]。
     根节点的值为 5 ，但是其右子节点值为 4 。

@see <a href="https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xn08xg/">链接</a>
 */

val root: TreeNode = TreeNode(5).apply {
    left = TreeNode(1)
    right = TreeNode(4).apply {
        left = TreeNode(3)
        right = TreeNode(6)
    }
}

fun main(){
    println(Question_xn08xg().isValidBST(root))
}

class Question_xn08xg {

    var pre = Long.MIN_VALUE

    /**
     * 思路：
     * 1、中序遍历，保证左树满足，再保证左子树比根小
     * 2、pre 的作用是引用遍历到目前位置最大的根节点，中序遍历是 左 -> 中 -> 右，所以只要保证每次遍历的时候节点都比 Pre 大即可。
     */
    fun isValidBST(root: TreeNode?): Boolean {
        if(root == null) return true
        if(!isValidBST(root.left))return false
        if(root.`val` <= pre) return false
        pre = root.`val`.toLong()
        return isValidBST(root.right)
    }
}