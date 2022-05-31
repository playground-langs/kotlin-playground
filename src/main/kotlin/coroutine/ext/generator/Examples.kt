package coroutine.ext.generator

fun main() {
    pythonGenerator()
    kotlinGenerator()
    fibonacci()
}

fun pythonGenerator() {
    val generator = generator<Int> { start ->
        for (i in 1..4) {
            yield(start + i)
        }
    }
    val nums = generator(10)
    for (n in nums) {
        println(n)
    }
}

fun kotlinGenerator() {
    val sequence = sequence<Int> {
        yield(1)
        yieldAll(listOf(2, 3, 4))
    }
    for (n in sequence) {
        println(n)
    }
}

fun fibonacci() {
    val fib = sequence<Long> {
        yield(1L)
        var current = 1L
        var next = 1L
        while (true) {
            yield(next)
            next += current
            current = next - current
        }
    }
    fib.take(10).forEach(::println)
}