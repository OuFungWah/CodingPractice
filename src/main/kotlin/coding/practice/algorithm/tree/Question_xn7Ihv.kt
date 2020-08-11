package coding.practice.algorithm.tree

import java.util.*

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
    val inLegalTree = TreeNode(1).apply {
        left = TreeNode(2).apply {
            right = TreeNode(3)
        }
        right = TreeNode(2).apply {
            right = TreeNode(3)
        }
    }
    val inLegalTree2 = TreeNode(1).apply {
        left = TreeNode(2).apply {
            left = TreeNode(2)
        }
        right = TreeNode(2).apply {
            left = TreeNode(2)
        }
    }
    println(Question_xn7Ihv().isSymmetric(legalTree))
    println(Question_xn7Ihv().isSymmetric(inLegalTree))
    println(Question_xn7Ihv().isSymmetric(inLegalTree2))
}

/**
对称二叉树
给定一个二叉树，检查它是否是镜像对称的。

 

例如，二叉树 [1,2,2,3,4,4,3] 是对称的。

1
/ \
2   2
/ \ / \
3  4 4  3
 

但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:

1
/ \
2   2
\   \
3    3
 

进阶：

你可以运用递归和迭代两种方法解决这个问题吗？

@see <a href="https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xn7ihv/">链接</a>
 */
class Question_xn7Ihv {

    /**
     * 思路：
     * 为了验证其是否对称，左必须和右一致，而左的右和右的左一致...以此类推
     * 两个指针同时往下遍历，一边遍历一边对比。
     */
    fun isSymmetric(root: TreeNode?): Boolean {
        return list(root?.left, root?.right)
    }

    fun list(left: TreeNode?, right: TreeNode?): Boolean{
        if(left  == null || right == null){
            return left == right
        }
        var flag = true
        flag = flag && list(left.left, right.right)
        flag = flag && left.`val` == right.`val`
        flag = flag && list(left.right, right.left)
        return flag
    }

}