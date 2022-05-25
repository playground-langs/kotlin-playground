package coroutineLite.thread

import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

/**
 * runnable不能响应中断 不能抛出中断异常
 * callable可以响应中断 可以抛出中断异常
 */

fun <R> asyncDoSomethingCancellable(
    action: () -> R,
    onSuccess: (R) -> Unit,
    onError: (Throwable) -> Unit
): Thread =
    thread {
        try {
            action().also { onSuccess(it) }
        } catch (e: Exception) {
            onError(e)
        }
    }

fun main() {
    testCancelableThread()
}

fun testCancelableThread() {
    val action: () -> String = {
        while (true) {
            //主动相应中断 中断响应点1
            if (Thread.interrupted()) {
                throw InterruptedException("manual interrupt")
            }
            //sleep等其他可以相应中断的函数
            //中断响应点2
            Thread.sleep(100)
        }
        "OK"
    }
    val onSuccess: (String) -> Unit = {
        println("get result success: $it")
    }
    val onError: (Throwable) -> Unit = {
        it.printStackTrace()
    }
    val thread = asyncDoSomethingCancellable(action, onSuccess, onError)
    //不添加sleep大概率在中断响应点1响应中断
    Thread.sleep(5)
    thread.interrupt()
}

/**
 * 循环内有中断响应点才能中断 且不能静默吞掉中断异常 需要重新抛出中断异常 传递中断
 */
fun testNoncancellableThread() {
    val action: () -> String = {
        measureTimeMillis {
            (1..10).forEach {
                try {
                    Thread.sleep(500)
                } catch (e: Exception) {
                    //捕获中断异常 会导致无法中断
                    e.printStackTrace()
                }
            }
        }.run(::println)
        "OK"
    }
    val onSuccess: (String) -> Unit = {
        println("get result success: $it")
    }
    val onError: (Throwable) -> Unit = {
        it.printStackTrace()
    }
    val thread = asyncDoSomethingCancellable(action, onSuccess, onError)
    thread.interrupt()
}