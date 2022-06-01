package coroutine.simple

import coroutine.log
import kotlinx.coroutines.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool


suspend fun main() {
    log("in main")
    testLaunch1()
    testLaunch2()
    testLaunch3()
    testLaunch4()
    testLaunch5()
}

/**
 * 系统提供的dispatcher默认为daemon
 */
fun testUseJavaPool() {
    //需要处理关闭 若是临时使用推荐使用use用完自动关闭
    Executors.newSingleThreadExecutor().asCoroutineDispatcher().use { dispatcher ->
        log("start")
        runBlocking {
            launch(dispatcher) {
                log("job1")
            }
            launch {
                log("job2")
            }
        }
        log("done")
    }
}


/**
 * 协程上下文存在继承合并关系
 * launch,async创建的协程继承父上下文属性并生成新的上下文 若未定义调度器且无拦截器则默认使用系统默认调度器
 * coroutineScope不创建新协程 使用外层协程上下文属性 主要作用是隔离协程作用域 封装协程作用域
 * runBlocking 创建全局作用域内的协程 并默认使用自定义的调度器BlockingEventLoop 该调度器包装当前线程作为单线程调度器
 * withContext 指定协程运行在特定上下文
 * GlobalScope 全局作用域 一般不要使用 会打破结构化并发 不关联任何Job 不会等待内部协程
 */
fun testLaunch1() {
    runBlocking {
        log("in runBlocking ${this.coroutineContext}")
        coroutineScope {
            log("coroutine ${this.coroutineContext}")
            launch {
                log("job1 ${this.coroutineContext}")
            }
            launch {
                log("job2 ${this.coroutineContext}")
            }
        }
    }
}

fun testLaunch2() {
    runBlocking(Dispatchers.Default) {
        log("in runBlocking")
        coroutineScope {
            launch {
                log("job1")
            }
            launch {
                log("job2")
            }
        }
    }
}

suspend fun testLaunch3() {
    coroutineScope {
        log("in coroutine")
        //使用默认调度器
        launch {
            log("job1")
        }
        launch {
            log("job2")
        }
    }
}

suspend fun testLaunch4() {
    coroutineScope {
        log("in coroutine")
        //使用默认调度器
        launch {
            log("job1")
        }
        withContext(ForkJoinPool.commonPool().asCoroutineDispatcher()) {
            log("in context")
            launch {
                log("job2")
            }
            log("in context")
        }
    }
}

/**
 * GlobalScope会打破结构化并发机制 不会等待内部协程完成
 */
suspend fun testLaunch5() {
    val latch = CountDownLatch(1)
    GlobalScope.launch {
        log("in global")
        //与主流程无关联的作用域 其他Job不会等待其完成
        coroutineScope {
            log("in coroutine")
            launch {
                log("in launch")
                latch.countDown()
            }
        }
    }
    //需要手动等待
    latch.await()
}