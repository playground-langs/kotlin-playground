package fluency.operatoroverload

import kotlin.math.abs

fun testCollectionPlusMinus() {
    val list1 = mutableListOf(1, 2, 3, 4, 5)
    val list2 = list1.plus(6) + 7
    val list3 = list2.minus(2) - 3
    println(list1)
    println(list2)
    println(list3)
}

/**
 * Pair<Int,Int>定义加法
 */
operator fun Pair<Int, Int>.plus(another: Pair<Int, Int>) = Pair(first + another.first, second + another.second)

fun testPairPlus() {
    val pair = (1 to 2) + (3 to 4)
    println("pair = $pair")
}

/**
 * 定义复数乘法
 */
data class Complex(val real: Int, val imaginary: Int) {
    operator fun times(other: Complex) =
        Complex(real * other.real - imaginary * other.imaginary, real * other.imaginary + imaginary * other.real)

    private fun sign() = if (imaginary < 0) "-" else "+"

    override fun toString(): String {
        return "$real ${sign()} ${abs(imaginary)}i"
    }
}

fun testComplex() {
    println(Complex(4, 2) * Complex(-3, 4))
    println(Complex(1, 2) * Complex(-3, 4))
}

/**
 * 不要改变操作符的操作数 产生新的数据 有编译器根据操作符语义进行处理
 */
data class Counter(val value: Int) {
    operator fun inc() = Counter(value + 1)
    operator fun dec() = Counter(value - 1)
    override fun toString(): String = "$value"
}

fun testCounter() {
    var counter = Counter(2)
    println(counter) //2
    println(++counter) //3
    println(counter) //3
    println(counter++) //3
    println(counter) //4
}