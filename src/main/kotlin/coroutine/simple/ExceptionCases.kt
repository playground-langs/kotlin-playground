package coroutine.simple

import kotlinx.coroutines.*
import kotlin.random.Random

/**
 * launch不会传播异常到调用方
 * 对于CancellationException引起的取消，它只会向下传播，取消子协程
 * 对于其他的异常引起的取消，它既可以向上取消，也可以向下传播，最终会导致所有协程被取消
 */
fun testLaunchWithException1() {
    runBlocking {
        try {
            val jobs = (1..10).map { i ->
                launch(Dispatchers.IO) {
                    test(i).run(::println)
                }
            }
            jobs.forEach { it.join() }
        } catch (e: Exception) {
            //无法捕获到携程内部的异常
            println("ERROR:${e.message}")
        }
    }
}

/**
 * SupervisorJob可以抑制异常对其他协程的影响
 */
fun testLaunchWithException2() {
    val handler = CoroutineExceptionHandler { context, ex ->
        println("caught:${ex.message} ${context[CoroutineName]}")
    }
    runBlocking {
        try {
            val jobs = (1..10).map { i ->
                launch(Dispatchers.IO + SupervisorJob() + CoroutineName("task-$i") + handler) {
                    test(i).run(::println)
                }
            }
            jobs.forEach { it.join() }
        } catch (e: Exception) {
            //无法捕获到携程内部的异常
            println("ERROR:${e.message}")
        }
    }
}

fun testLaunchWithException3() {
    val handler = CoroutineExceptionHandler { context, ex ->
        println("caught:${ex.message} ${context[CoroutineName]}")
    }
    runBlocking {
        try {
            coroutineScope {
                val jobs = (1..10).map { i ->
                    launch(Dispatchers.IO + CoroutineName("task-$i") + handler) {
                        test(i).run(::println)
                    }
                }
                jobs.forEach { it.join() }
            }
        } catch (e: Exception) {
            //可以捕获到携程内部传播出来的异常
            println("ERROR:${e.message}")
        }
    }
}

fun testAsyncWithException4() {
    //对于async异常处理程序无意义
    val handler = CoroutineExceptionHandler { context, ex ->
        println("caught:${ex.message} ${context[CoroutineName]}")
    }
    runBlocking {
        try {
            val jobs = (1..10).map { i ->
                async(Dispatchers.IO + CoroutineName("task-$i") + handler) {
                    test(i).run(::println)
                }
            }
            jobs.forEach { it.await() }
        } catch (e: Exception) {
            //可以捕获到携程内部传播出来的异常
            println("ERROR:${e.message}")
        }
    }
}

fun test(n: Int) =
    when (n) {
        3 -> throw IllegalArgumentException("$n")
        else -> {
            Thread.sleep(Random.nextLong(1000))
            n
        }
    }

fun main() {
    testAsyncWithException4()
}