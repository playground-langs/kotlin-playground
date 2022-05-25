package async.future

import async.url1
import async.url2
import async.url3
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.system.measureTimeMillis

val singleExecutor: ExecutorService by lazy {
    Executors.newSingleThreadExecutor()
}
var error = false
fun jsonFuture(url: String, args: String): Future<String> {
    return singleExecutor.submit(Callable {
        if (error && url == url2) {
            throw IllegalArgumentException()
        }
        //IO
        Thread.sleep(500)
        "$url $args"
    })
}

/**
 * 阻塞主线程影响效率
 */
fun main() {
    error = false
    try {
        measureTimeMillis {
            val r1 = jsonFuture(url1, "").get()
            val r2 = jsonFuture(url2, r1).get()
            val r3 = jsonFuture(url3, r2).get()
            println(r3)
        }.run(::println)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    singleExecutor.shutdownNow()
}