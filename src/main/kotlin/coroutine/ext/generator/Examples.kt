package coroutine.ext.generator

fun main() {
    pythonGenerator()
    kotlinGenerator()
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