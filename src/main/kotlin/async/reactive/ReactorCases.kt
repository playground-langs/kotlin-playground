package async.reactive

import async.url1
import async.url2
import async.url3
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.flux
import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Flux
import java.util.concurrent.SynchronousQueue

/**
 * 原生挂起函数
 */
suspend fun jsonAsync(url: String, args: String): String {
    //IO
    delay(500)
    if (async.coroutine.error && url == url2) {
        throw IllegalArgumentException()
    }
    println(url)
    return "$url $args"
}

suspend fun main() {
    testBetterWay()
    reactorWay()
}

/**
 * 回调地狱式使用 不推荐
 * 此处最好使用协程通信方式获取数据如channel
 */
fun testCallbacks() {
    val queue = SynchronousQueue<String>()
    mono {
        jsonAsync(url1, "")
    }.subscribe { r1 ->
        mono {
            jsonAsync(url2, r1)
        }.subscribe { r2 ->
            mono {
                jsonAsync(url3, r2)
            }.subscribe { r3 ->
                println("$r3 ${Thread.currentThread()}")
                queue.put(r3)
            }
        }
    }
    val r3 = queue.take()
    println("$r3 ${Thread.currentThread()}")
}

/**
 * 正确用法 协程结合reactor使用效果并不好
 */
suspend fun testBetterWay() {
    val mono = mono {
        jsonAsync(url1, "")
    }.flatMap { r1 ->
        mono {
            jsonAsync(url2, r1)
        }
    }.flatMap { r2 ->
        mono {
            jsonAsync(url3, r2)
        }
    }
    //需要触发否则无法获取数据
    val r3 = mono.awaitSingle()
    println("$r3 ${Thread.currentThread()}")
}

/**
 * 流更适合处理流式冷数据(订阅才会产生)
 */
suspend fun reactorWay() {
    val flux = flux {
        val range = (1..10)
        range.forEach {
            send(it)
        }
    }
    flux.collectList().awaitSingle().run(::println)
    Flux.range(1, 10).subscribe(::println)
}