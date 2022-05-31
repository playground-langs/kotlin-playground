package coroutine.simple

import coroutine.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() {
    GlobalScope.launch {
        log("global")
    }
    coroutineScope {
        delay(100)
        log("coroutine")
    }
    log("main")
}