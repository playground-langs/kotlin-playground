package contract

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 *
 *Contracts的使用目前有以下限制:
 *1.只能在top-level的函数中使用Contracts，不能用在类的成员方法上
 *2.函数体中如果有Contracts，必须出现在第一句
 *3.编译器无条件地信任Contracts，所以开发者必须保证Contracts的正确性
 *4.Contract中只能引用参数本身,不能引用参数的属性
 */

/**
 * CallInPlace契约指定调用情况
 * 如let also run apply等
 */
/**
 * 若不指定调用情况无法处理一些特殊情况
 */
@OptIn(ExperimentalContracts::class)
fun myRun(block: () -> Unit): Unit {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block()
}

fun testValAssignment() {
    val i: Int
    myRun {
        i = 10
    }
    println(i)
}
