package coroutine.ext.generator

import kotlin.coroutines.*

interface Generator<T> {
    operator fun iterator(): Iterator<T>
}

interface GeneratorScope<T> {
    suspend fun yield(value: T)
}

sealed class State {
    class NotReady(val continuation: Continuation<Unit>) : State()
    class Ready<T>(val continuation: Continuation<Unit>, val nextValue: T) : State()
    object Done : State()
}

class GeneratorImpl<T>(val start: T, val block: suspend GeneratorScope<T>.(T) -> Unit) : Generator<T> {
    override fun iterator(): Iterator<T> = GeneratorIterator(start, block)
}

class GeneratorIterator<T>(
    start: T,
    block: suspend GeneratorScope<T>.(T) -> Unit
) : Iterator<T>, GeneratorScope<T>, Continuation<Any?> {
    private var state: State

    init {
        val coroutineBlock: suspend GeneratorScope<T>.() -> Unit = { block(start) }
        val continuation = coroutineBlock.createCoroutine(this, this)
        state = State.NotReady(continuation)
    }

    private fun resume() {
        when (val currentState = state) {
            is State.NotReady -> currentState.continuation.resume(Unit)
            else -> Unit
        }
    }

    override fun hasNext(): Boolean {
        resume()
        return state != State.Done
    }

    override fun next(): T {
        resume()
        return when (val currentState = state) {
            is State.NotReady -> {
                resume()
                return next()
            }
            is State.Ready<*> -> {
                state = State.NotReady(currentState.continuation)
                (currentState as State.Ready<T>).nextValue
            }
            State.Done -> throw IndexOutOfBoundsException("no value left")
        }
    }

    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    override fun resumeWith(result: Result<Any?>) {
        state = State.Done
    }

    /**
     * 单线程使用暂不考虑线程安全问题
     */
    override suspend fun yield(value: T) {
        suspendCoroutine<Unit> { continuation ->
            state = when (state) {
                is State.NotReady -> State.Ready(continuation, value)
                is State.Ready<*> -> throw IllegalStateException("can't yield while ready")
                State.Done -> throw IllegalStateException("can't yield while done")
            }
        }
    }
}

fun <T> generator(block: suspend GeneratorScope<T>.(T) -> Unit): (T) -> Generator<T> {
    return { start ->
        GeneratorImpl(start, block)
    }
}
