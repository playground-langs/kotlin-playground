package fp

/**
 * reduce实现原理 第一个元素赋值给第一个参数(累计值) 后续元素依次应用lambda返回值赋值给累计值 最终返回累计值
 */
fun testReduce() {
    println(listOf(1, 2, 3).reduce { total, e -> total + e })
    println(listOf("a", "b", "c", "d").map { it.uppercase() }.reduce { total, s -> "$total,$s" })
}