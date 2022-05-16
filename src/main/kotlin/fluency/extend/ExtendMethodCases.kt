package fluency.extend

data class Point(val x: Int, val y: Int)

data class Circle(val cx: Int, val cy: Int, val radius: Int)

/**
 * 底层实现为静态方法 第一个参数为调用对象实例
 * 扩展函数仅能访问到当前位置可访问数据
 * 当扩展函数与实例方法冲突时总是以实例方法为准
 */
fun Circle.contain(point: Point) = (point.x - cx) * (point.x - cx) + (point.y - cy) * (point.y - cy) < radius * radius

fun testContains() {
    val circle = Circle(100, 100, 25)
    val point1 = Point(110, 110)
    val point2 = Point(10, 100)
    println(circle.contain(point1))
    println(circle.contain(point2))
}


fun String.isPalindrome(): Boolean {
    println("version 1")
    return reversed() == this
}