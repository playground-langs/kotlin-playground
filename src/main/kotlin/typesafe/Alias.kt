package typesafe

import oop.oo.Person as P

typealias Q = oop.oo.Person

fun main() {
    //交换参数顺序
    println(test(B(1), A(2)))
    //使用import as和typealias
    println(P("tom") == Q("tom"))
}

typealias A = Animal
typealias B = Animal

/**
 * 别名底层相同则可替换
 */
fun test(a: A, b: B): Boolean {
    return a == b
}