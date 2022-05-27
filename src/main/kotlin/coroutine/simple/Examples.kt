package coroutine.simple

import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.coroutines.*

/**
 * 创建协程并手动启动协程
 */
fun createThenStartCoroutine() {
    //创建协程
    val continuation = suspend {
        println("start in continuation")
        println("end in continuation")
        5
    }.createCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println("callback start")
            println("callback end:continuation result:${result.getOrThrow()}")
        }
    })
    //启动协程
    continuation.resume(Unit)
}

/**
 * 创建同时自动启动协程
 */

fun startCoroutine() {
    suspend {
        println("start and run right now")
    }.startCoroutine(object : Continuation<Unit> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Unit>) {
            println("coroutine end: ${result.getOrNull()}")
        }
    })
}

/**
 * kotlin无法直接创建带有receiver的lambda 需要借助编译器自动推断
 */
fun <R, T> launchCoroutine(receiver: R, block: suspend R.() -> T) {
    block.startCoroutine(receiver, object : Continuation<T> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<T>) {
            println("$receiver $result")
        }
    })
}

class ProducerScope<T> {
    suspend fun produce(value: T) {
        delay(1000)
        println("produce $value ${LocalTime.now()}")
    }
}

/**
 * 创建带有receiver的协程
 * 便于调用receiver的方法
 */
fun testCoroutineWithReceiver() {
    //显式使用Continuation编程函数不用声明为suspend (suspend的作用是隐式传递continuation)
    launchCoroutine(ProducerScope<Int>()) {
        println("start produce 1024 ${LocalTime.now()}")
        produce(1024)
        println("start delay ${LocalTime.now()}")
        delay(1000)
        println("start produce 2048 ${LocalTime.now()}")
        produce(2048)
    }
}

/**
 * 使用receiver(一般称为作用域)不仅可以提供快捷函数
 * 还可以限制协程体
 */
fun testCoroutineRestricted() {
    launchCoroutine(RestrictedProducerScope<Int>()) {
        produce(1024)
        //只能调用RestrictedProducerScope内的挂起函数
        //delay(100)
    }
}

/**
 * 限制只能调用scope指定的挂起函数
 */
@RestrictsSuspension
class RestrictedProducerScope<T> {
    suspend fun produce(value: T) {

    }
}

fun main() {
    //调用链一个函数挂起 所有相同协程上的祖先函数全部挂起 并从协程的根节点挂起函数(此处为testCoroutineWithReceiver)之后继续运行
    //猜测协程的实现:由continuation串联调用关系 挂起时找到root continuation继续执行
    testCoroutineWithReceiver()
    println("main end ${LocalTime.now()}")
    //此处需要主线程等待协程处理完毕
    //todo 更优雅的处理方式
    Thread.sleep(4000)
}