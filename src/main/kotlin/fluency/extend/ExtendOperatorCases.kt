package fluency.extend


operator fun Circle.contains(point: Point) =
    (point.x - cx) * (point.x - cx) + (point.y - cy) * (point.y - cy) < radius * radius

fun testOperatorIn() {
    val circle = Circle(100, 100, 25)
    val point1 = Point(110, 110)
    val point2 = Point(10, 100)
    println(circle.contains(point1))
    println(circle.contains(point2))
    println(point1 in circle)
    println(point2 in circle)
}

operator fun ClosedRange<String>.iterator() = object : Iterator<String> {

    private val last = endInclusive

    private val next = StringBuilder(start)

    override fun hasNext(): Boolean = last >= next.toString() && last.length >= next.length

    override fun next(): String {
        val result = next.toString()
        val lastChar = next.last()
        if (lastChar < Char.MAX_VALUE) {
            next.setCharAt(next.length - 1, lastChar + 1)
        } else {
            next.append(Char.MIN_VALUE)
        }
        return result
    }
}

fun testStringRangeIterator() {
    for (word in "hell".."help") {
        print("$word ,")
    }
}

