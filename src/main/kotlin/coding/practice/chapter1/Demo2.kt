package coding.practice.chapter1

// 数据变化操作
sealed class OPERATOR {
    // 每次新消息回调，在队末新添置数据
    class APPEND(val items: List<String>) : OPERATOR()

    // 刷新具体某个数据 Item
    class UPDATE(val position: Int, val item: String) : OPERATOR()

    // 重新加载数据
    class RELOAD(val items: List<String>) : OPERATOR()

    // 清空数据
    class CLEAR() : OPERATOR()
}

fun main() {
    // UI 层
    print(0x0f)

}