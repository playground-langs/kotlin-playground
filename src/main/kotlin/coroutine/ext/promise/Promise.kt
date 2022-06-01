package coroutine.ext.promise

import java.util.concurrent.CompletableFuture
import kotlin.coroutines.*

interface AsyncScope

fun async(context: CoroutineContext = EmptyCoroutineContext, block: suspend AsyncScope.() -> Unit) {
    val completion = AsyncCoroutine(context)
    block.startCoroutine(completion, completion)
}

class AsyncCoroutine(override val context: CoroutineContext = EmptyCoroutineContext) : Continuation<Unit>, AsyncScope {
    override fun resumeWith(result: Result<Unit>) {
        result.getOrThrow()
    }
}

suspend fun <T> AsyncScope.await(block: () -> T) =
    suspendCoroutine<T> { continuation ->
        CompletableFuture.supplyAsync(block)
            .thenAccept {
                continuation.resume(it)
            }.exceptionally {
                continuation.resumeWithException(it)
                null
            }
    }
