package coroutine.simple

fun main() {
    testSequence()
    testIterator()
}

fun testSequence() {
    val fib = sequence<Long> {
        var current = 1L
        yield(current)
        var next = 1L
        while (true) {
            yield(next)
            next += current
            current = next - current
        }
    }
    fib.take(10).toList().run(::println)
}

fun testIterator() {
    for (s in "aa".."am") {
        println(s)
    }
}

operator fun ClosedRange<String>.iterator() = iterator {
    val last = endInclusive
    val next = StringBuilder(start)

    fun hasNext(): Boolean = last >= next.toString() && last.length >= next.length
    fun next(): String {
        val result = next.toString()
        val lastChar = next.last()
        if (lastChar < Char.MAX_VALUE) {
            next.setCharAt(next.length - 1, lastChar + 1)
        } else {
            next.append(Char.MIN_VALUE)
        }
        return result
    }
    while (hasNext()) {
        println("use coroutine")
        yield(next())
    }
}