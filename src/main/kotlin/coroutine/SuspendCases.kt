package coroutine

import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun jsonSuspendable(url: String): String =
    suspendCoroutine { continuation ->
        thread {
            try {
                continuation.resume(download(url))
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

fun download(url: String): String {
    return "$url ok"
}

suspend fun main() {
    jsonSuspendable("www.test.com").run(::println)
}