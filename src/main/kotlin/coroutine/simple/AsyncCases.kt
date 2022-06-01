package coroutine.simple

import coroutine.log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

suspend fun main() {
    testAsync()
}

/**
 * coroutineScope创建的协程运行在父协程所在线程或Dispatcher
 */
suspend fun testAsync() {
    coroutineScope {
        log("get current thread $this")
        yield()
        log("get current thread $this")
    }
    log("main start")
    coroutineScope {
        log("coroutine start $this")
        launch {
            log("async no suspend 1 $this")
            coroutineScope {
                log("inner")
            }
        }
        launch {
            log("async no suspend 2 $this")
        }
        log("coroutine end $this")
    }
    log("main end")
}