package async.coroutine

import async.url1
import async.url2
import async.url3
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

var error = false

/**
 * 原生挂起函数
 */
suspend fun jsonAsync(url: String, args: String): String {
    //IO
    delay(500)
    if (error && url == url2) {
        throw IllegalArgumentException()
    }
    println(url)
    return "$url $args"
}

/**
 *  推荐使用
 */
suspend fun testSuspend() {
    val start = System.currentTimeMillis()
    coroutineScope {
        try {
            val r1 = jsonAsync(url1, "")
            val r2 = jsonAsync(url2, r1)
            val r3 = jsonAsync(url3, r2)
            println(r3)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    println(System.currentTimeMillis() - start)
}

suspend fun main() {
    error = false
    testSuspend()
}