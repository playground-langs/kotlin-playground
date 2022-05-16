package fluency.extend

/**
 * 可以给lambda扩展方法
 */
fun <T, R, U> ((T) -> R).andThen(next: (R) -> U): (T) -> U = {
    next(this(it))
}

fun increment(number: Int): Double = number + 1.toDouble()

fun double(number: Double) = number * 2

fun testAndThen() {
    val incrementAndDouble = ::increment.andThen(::double)
    println(incrementAndDouble(5))
}