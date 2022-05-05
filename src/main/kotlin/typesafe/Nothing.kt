package typesafe

fun main() {
    println(sqrt(10.0))
    neverReach()
}

/**
 *Nothing用于验证类型完整性
 */
fun sqrt(input: Double): Double {
    if (input >= 0) {
        return kotlin.math.sqrt(input)
    } else {
        //返回Nothing
        throw IllegalArgumentException("input $input must > 0")
    }
}

fun neverReach(): Nothing {
    throw UnsupportedOperationException("")
}