package typesafe.generic.boolext.v1

/**
 * 实现Boolean扩展实现链式调用避免if-else打断链式调用
 * v1:无返回值版本
 */

/**
 * 中间对象 承载yes的返回值并作为otherwise的调用方
 */
sealed class BooleanExt

object Otherwise : BooleanExt()

object TransferData : BooleanExt()

fun Boolean.yes(block: () -> Unit): BooleanExt = when {
    this -> {
        block.invoke()
        TransferData
    }
    else -> {
        Otherwise
    }
}

fun BooleanExt.otherwise(block: () -> Unit) = when (this) {
    is Otherwise -> block.invoke()
    else -> Unit
}

fun main() {
    val list = listOf(1, 2, 3)
    (list.size == 3).yes { println("yes") }.otherwise { println("no") }
}