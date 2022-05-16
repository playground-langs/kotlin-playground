package fluency.infix

import fluency.extend.Circle
import fluency.extend.Point
import fluency.extend.contain

infix fun Circle.include(point: Point) = this.contain(point)

fun testInfix() {
    println(Circle(100, 110, 25) include Point(110, 110))
}