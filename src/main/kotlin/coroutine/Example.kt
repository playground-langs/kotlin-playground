package coroutine

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

/**
 * 结构化并发:带有作用域和生命周期管理的并发编程模式
 */

fun task1() {
    println("start task1 in thread:${Thread.currentThread()}")
    println("end task1 in thread:${Thread.currentThread()}")
}

fun task2() {
    println("start task2 in thread:${Thread.currentThread()}")
    println("end task2 in thread:${Thread.currentThread()}")
}

suspend fun task1Async() {
    println("start task1 in thread:${Thread.currentThread()}")
    yield()
    println("end task1 in thread:${Thread.currentThread()}")
}

suspend fun task2Async() {
    println("start task2 in thread:${Thread.currentThread()}")
    yield()
    println("end task2 in thread:${Thread.currentThread()}")
}

fun runTaskSync() {
    println("start")
    run {
        task1()
        task2()
        println("call task1 and task2 from ${Thread.currentThread()}")
    }
    println("done")
}

/**
 * 协程默认继承父上下文 包括执行线程
 */
fun runTaskAsyncInMain() {
    println("start")
    runBlocking {
        launch { task1Async() }
        launch { task2Async() }
        println("call task1 and task2 from ${Thread.currentThread()}")
    }
    println("done")
}

fun main() {
    runTaskAsyncInMain()
}