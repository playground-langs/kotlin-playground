package oop.oo

/**
 * 定义与类名相同的函数作为工厂方法
 */
class Tester(val name: String, val age: Int) {
    constructor(age: Int) : this("unknown", age)
}

/**
 * 可以在其他模块定义与类名相同的函数 使用起来与构造函数体验一致
 */
fun Tester(pair: Pair<String, Int>): Tester = Tester(pair.first, pair.second)

