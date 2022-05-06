package typesafe

import kotlin.random.Random

fun main() {
    val a = Animal(10)
    val b = Animal(10)
    val c = Animal(15)
    println(a === b)
    println(a == b)
    println(a == c)

    val any = getAny()
    //as转换类型不匹配会报异常
    val message: String = any as? String ?: ""
    println(message)
}

fun testWhen(any: Any) {
    when (any) {
        is Int -> println(any.inc())
        is String -> println(any.length)
        else -> println("do nothing")
    }
}

fun getAny(): Any = if (Random.nextInt(10) > 5) 1 else "low than 5"