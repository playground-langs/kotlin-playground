package coroutine.simple

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 挂起函数只能在协程体(suspend lambda)内部或挂起函数中调用 需要传递continuation
 */

suspend fun main() {
    //main
    println("where am I ${Thread.currentThread()}")
    delayLittle()
    //coroutine
    println("main end ${Thread.currentThread()}")
}

suspend fun delayLittle() {
    //main
    println("where am I ${Thread.currentThread()}")
    coroutineScope {
        delay(5000)
        println("sync in coroutine ${Thread.currentThread()}")
    }
    //coroutine
    println("where am I ${Thread.currentThread()}")
}

suspend fun syncFun(): Int {
    return 1
}

/**
 * 编译器传入并维护suspend函数所需continuation
 */
suspend fun suspendFun(): Int =
    suspendCoroutine<Int> { continuation ->
        //此处发生异步调用才会真正挂起
        thread {
            //通过resume返回结果
            continuation.resume(5)
        }
    }

/**
 * 此函数不会真正挂起 未发生异步调用
 *
 * 异步调用是否发生，取决于resume函数与对应的挂起函数的调用是否在相同的调用栈上，
 * 切换函数调用栈的方法可以是切换到其他线程上执行，
 * 也可以是不切换线程但在当前函数返回之后的某一个时刻再执行
 * suspend lambda内无异步则不会真正挂起
 */
suspend fun notSuspend(): Int = suspendCoroutine { continuation ->
    //同步调用
    continuation.resume(5)
}
