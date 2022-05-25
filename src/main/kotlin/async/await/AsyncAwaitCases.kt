package async.await

import async.url1
import async.url2
import async.url3
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

var error = false

/**
 * 同步函数
 */
fun jsonSync(url: String, args: String): String {
    //IO
    Thread.sleep(500)
    if (error && url == url2) {
        throw IllegalArgumentException()
    }
    println(url)
    return "$url $args"
}

suspend fun main() {
    error = true
    testAsyncAwait()
}

/**
 * 同步转异步
 */
suspend fun testAsyncAwait() {
    val start = System.currentTimeMillis()
    coroutineScope {
        val r1 = async {
            jsonSync(url1, "")
        }
        val r2 = async {
            jsonSync(url2, r1.await())
        }
        val r3 = async {
            jsonSync(url3, r2.await())
        }
        println(r3.await())
    }
    println(System.currentTimeMillis() - start)
}