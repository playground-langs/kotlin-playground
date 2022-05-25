package async.promise

import async.url1
import async.url2
import async.url3
import java.util.concurrent.CompletableFuture
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
    jsonCompletableFuture(url1, "")
        .thenApply { r1 ->
            jsonCompletableFuture(url2, r1)
                .thenApply { r2 ->
                    jsonCompletableFuture(url3, r2)
                        .thenApply { println(it) }
                        .exceptionally { println(it) }
                }.exceptionally {
                    println(it)
                    null
                }
        }.exceptionally {
            it.printStackTrace()
            null
        }
    //默认ForkJoinPool线程为daemon 后台线程
    Thread.sleep(2000)
}

/**
 * 正确 推荐使用
 */
fun testBetterWay() {
    jsonCompletableFuture(url1, "")
        .thenCompose { r1 ->
            jsonCompletableFuture(url2, r1)
        }.thenCompose { r2 ->
            jsonCompletableFuture(url3, r2)
        }.thenApply {
            println(it)
        }.exceptionally {
            it.printStackTrace()
        }
    //默认ForkJoinPool线程为daemon 后台线程
    Thread.sleep(2000)
}