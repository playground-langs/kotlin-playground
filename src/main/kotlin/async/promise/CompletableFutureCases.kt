package async.promise

import async.url1
import async.url2
import async.url3
import java.util.concurrent.CompletableFuture
import java.util.concurrent.SynchronousQueue
import kotlin.system.measureTimeMillis

/**
 * js中Promise类似于Java中的CompletableFuture
 */
var error = false
fun jsonCompletableFuture(url: String, args: String): CompletableFuture<String> =
    CompletableFuture.supplyAsync {
        //测试异常处理
        if (error && url == url2) {
            throw IllegalStateException()
        }
        //IO
        Thread.sleep(500)
        "$url $args"
    }

fun main() {
    error = false
    measureTimeMillis {
        testCallbackHell()
    }.run(::println)
    measureTimeMillis {
        testBetterWay()
    }.run(::println)
}

/**
 * thenApply和exceptionally需要成对出现才能涵盖所有可能异常
 * 错误的使用实例
 */
fun testCallbackHell() {
    //使用同步移交队列在主线程中拿到结果 主线程无法获取产生r3响应的future无法get
    val queue = SynchronousQueue<String>()
    //与Promise类似每个操作(如thenApply等)都会产生新的Future
    jsonCompletableFuture(url1, "")
        .thenApply { r1 ->
            jsonCompletableFuture(url2, r1)
                .thenApply { r2 ->
                    jsonCompletableFuture(url3, r2)
                        .thenApply { r3 ->
                            queue.put(r3)
                            println("$r3 ${Thread.currentThread()}")
                        }
                        .exceptionally { println(it) }
                }.exceptionally {
                    println(it)
                    null
                }
        }.exceptionally {
            it.printStackTrace()
            null
        }
    //阻塞等待异步响应
    val r3 = queue.take()
    println("$r3 ${Thread.currentThread()}")
}

/**
 * 默认ForkJoinPool线程为daemon后台线
 * 正确 推荐使用
 * 缺点:结果的获取脱离了主调用流程 亦即 主线程后续流程中无法非阻塞的获取调用结果 get()未完成时会阻塞主线程
 */
fun testBetterWay() {
    val future = jsonCompletableFuture(url1, "")
        .thenCompose { r1 ->
            jsonCompletableFuture(url2, r1)
        }.thenCompose { r2 ->
            jsonCompletableFuture(url3, r2)
        }
    future.thenApply {
        println("$it ${Thread.currentThread()}")
    }.exceptionally {
        it.printStackTrace()
    }
    //必须获取对应的Future才能获取相应结果
    val r3 = future.get()
    //主线程中很难非阻塞获取异步结果 需要调用get阻塞主线程
    println("$r3 ${Thread.currentThread()}")
}