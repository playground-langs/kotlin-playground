package async.callback

import async.url1
import async.url2
import async.url3
import com.google.common.util.concurrent.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import kotlin.system.measureTimeMillis

val singleExecutor: ListeningExecutorService by lazy {
    MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1) as ThreadPoolExecutor)
}
var error = false
fun jsonFuture(url: String, args: String): ListenableFuture<String> {
    return singleExecutor.submit(Callable {
        if (error && url == url2) {
            throw IllegalArgumentException()
        }
        Thread.sleep(500)
        //IO
        "$url $args"
    })
}

fun main() {
    error = true
    measureTimeMillis {
        testCallback()
    }.run(::println)
    Thread.sleep(3000)
    singleExecutor.shutdownNow()
}

/**
 * 回调地狱
 * 代码结构异常复杂
 * 不推荐使用
 */
fun testCallback() {
    val future1 = jsonFuture(url1, "")
    Futures.addCallback(future1, object : FutureCallback<String> {
        override fun onSuccess(r1: String?) {
            val future2 = jsonFuture(url2, r1!!)
            Futures.addCallback(future2, object : FutureCallback<String> {
                override fun onSuccess(r2: String?) {
                    val future3 = jsonFuture(url3, r2!!)
                    Futures.addCallback(future3, object : FutureCallback<String> {
                        override fun onSuccess(r3: String?) {
                            println(r3)
                        }

                        override fun onFailure(t: Throwable) {
                            t.printStackTrace()
                        }
                    }, singleExecutor)
                }

                override fun onFailure(t: Throwable) {
                    t.printStackTrace()
                }

            }, singleExecutor)
        }

        override fun onFailure(t: Throwable) {
            t.printStackTrace()
        }

    }, singleExecutor)
}