package typesafe

fun main() {
    f1()
    println(increment(1))
    println(increment(null))
    println(increment1(1))
    println(increment1(null))
}

fun f1() {
    val nullable: Any? = null
    println(nullable.toString())
}

fun increment(adder: Int?): Int? =
    if (adder != null) {
        adder + 1
    } else {
        null
    }

fun increment1(adder: Int?): Int? {
    return adder?.plus(1)
}

fun increment2(adder: Int?): Int {
    val result = adder?.plus(1)
    return if (result == null) Int.MIN_VALUE else result
}

/**
 * elvis ?:
 */
fun increment3(adder: Int?): Int {
    return adder?.plus(1) ?: Int.MIN_VALUE
}

/**
 * 非空断言 !! 停止kotlin null检查，由开发人员保证，不要使用
 */
fun increment4(adder: Int?): Int {
    return adder!!.plus(1)
}

/**
 * 使用安全调用或Elvis运算符来提取值，并使用when对可空的引用做出决策
 */
fun increment5(adder: Int?): Unit = when (adder) {
    null -> println("get null,do nothing")
    else -> println(adder + 1)
}
