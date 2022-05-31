package coroutine.simple

import coroutine.log
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine


fun main() {
    suspendFunc()
    suspendFunc()
}

fun suspendFunc() {
    suspend {
        thread {
            log("coroutine/thread")
        }
        1
    }.startCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = LogInterceptor()

        override fun resumeWith(result: Result<Int>) {
            log("resume with ${result.getOrNull()}")
        }
    })
}

class LogInterceptor : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return LogContinuation(continuation)
    }
}

class LogContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> by continuation {
    override fun resumeWith(result: Result<T>) {
        log("before resume:$result")
        continuation.resumeWith(result)
        log("after resume:$result")
    }
}