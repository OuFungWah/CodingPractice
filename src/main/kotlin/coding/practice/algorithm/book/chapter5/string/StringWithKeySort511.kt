package coding.practice.algorithm.book.chapter5.string

val a = arrayListOf(
        "Anderson" to 2,
        "Brown" to 3,
        "Davis" to 3,
        "Garcia" to 4,
        "Harris" to 1,
        "Jackson" to 4,
        "Jones" to 3,
        "Martin" to 1,
        "Martinez" to 2,
        "Miller" to 2,
        "Moore" to 1,
        "Robinson" to 2,
        "Smith" to 4,
        "Taylor" to 3,
        "Thomas" to 4,
        "Thompson" to 4,
        "White" to 2,
        "Williams" to 3,
        "Wilson" to 4
)

fun main() {
    println(StringWithKeySort511().method(a))
}

/**
 *
 */
class StringWithKeySort511 {

    /**
     *
     * 键索引算法。
     *
     * 适用于键值为较小 Int 值且键数量不多的情况。
     *
     * 主要思想：
     * 1、首次遍历数组 a，计算所有分组的各自个数存入 count，分组编号用作下标
     * 2、第二次遍历 count 数组，计算各个分组的理应初始下标
     *      count 数组 before: [0, 3, 5, 5, 6]
     *      count 数组 after:  [0, 0, 3, 8, 13]
     * 3、再次遍历数组 a，用分组为 index ，找到 count 中的对应的初始位置并插入，然后 count 对应的分组初始位置++
     *      处理： ["Anderson", 2]
     *      count 数组 before: [0, 0, 3, 8, 13]
     *      ["Anderson", 2] 插入到 sortArr[3] 中，并且将 count 对应数值++
     *      count 数组 after: [0, 0, 4, 8, 13]
     */
    fun <T> method(origin: ArrayList<Pair<T, Int>>): ArrayList<Pair<T, Int>> {
        val count = Array(findBiggest() + 1) { 0 }
        // 统计数量
        origin.forEach {
            count[it.second]++
        }
        var start = 0
        // 搭建索引表
        count.forEachIndexed { index, num ->
            if (index == 0) return@forEachIndexed
            count[index] = start
            start += num
        }
        println(origin.size)
        val sortedArr = ArrayList(origin)
        println(sortedArr.size)
        // 分组排序
        origin.forEach {
            sortedArr[count[it.second]] = it.first to it.second
            count[it.second]++
        }
        return sortedArr
    }

    fun findBiggest(): Int {
        var biggest = 0
        a.forEach {
            if (it.second > biggest) {
                biggest = it.second
            }
        }
        return biggest
    }

}