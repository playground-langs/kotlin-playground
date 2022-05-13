package fp

/**
 * 纯函数lambda
 */
val doubleIt = { e: Int -> e * 2 }

var factor = 2

/**
 * 带外部变量的lambda 即为闭包 尽量使用纯函数，闭包会使代码复杂同时引入可变性
 */
val doubleItClosure = { e: Int -> e * factor }

fun main() {
    println(doubleIt(2))
    println(doubleItClosure(2))
    factor = 4
    println(doubleItClosure(2))
}