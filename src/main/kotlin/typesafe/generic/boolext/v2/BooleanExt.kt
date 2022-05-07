package typesafe.generic.boolext.v2

/**
 * 实现Boolean扩展实现链式调用避免if-else打断链式调用
 * v2:无返回值版本
 */

/**
 * 中间对象 承载yes的返回值并作为otherwise的调用方
 * Any? 是所有类型的父类型
 * Nothing是所有类的子类型
 *
 * 泛型设计思路:
 * 1.观察泛型参数位置确定 协变/不变 逆变/不变
 * 2.以不变的方式实现,若无子类型化需求则完成
 * 3.选择协变或逆变
 *
 */
sealed class BooleanExt<out T>

object Otherwise : BooleanExt<Nothing>()

class TransferData<T>(val data: T) : BooleanExt<T>()

fun <T> Boolean.yes(block: () -> T): BooleanExt<T> = when {
    this -> TransferData(block.invoke())
    else -> Otherwise
}

fun <T> BooleanExt<T>.otherwise(block: () -> T): T = when (this) {
    is Otherwise -> block.invoke()
    is TransferData -> this.data
}

fun main() {
    val list = listOf(1, 2, 3)
    val test = list.size == 3
    val r1 = test.yes { println("yes") }.otherwise { println("no") }
    println(r1)
    val r2 = test.yes { 10 }.otherwise { "test" }
    println(r2)
}