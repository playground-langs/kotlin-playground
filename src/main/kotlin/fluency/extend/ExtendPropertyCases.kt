package fluency.extend

import kotlin.math.PI

/**
 * 不能使用幕后字段
 */
val Circle.area: Double
    get() = PI * radius * radius

fun testExtendProperty() {
    val circle = Circle(100, 100, 25)
    println("Area is ${circle.area}")
}