package coroutine.ext.generator

fun main() {
    pythonGenerator()
    kotlinGenerator()
}

fun pythonGenerator() {
    val generator = generator<Int> {
        yield(1)
        yield(2)
        yield(3)
        yield(4)
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