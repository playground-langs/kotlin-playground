package coroutine.simple

import coroutine.log
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 通过Job维护的协程才能具有结构化并发能力
 * 不建议直接使用简单协程
 */
suspend fun main() {
    log("start")
    coroutineScope {
        launch {
            delay(2000)
            log("launch")
        }
        async {
            delay(3000)
            log("async")
        }
    }
    log("end")
}