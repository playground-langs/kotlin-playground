package coroutine.simple

import coroutine.log
import kotlinx.coroutines.*


suspend fun main() {
    cancelJob()
}

/**
 * 通过Job维护的协程才能具有结构化并发能力:协程具有父子关系 完成和取消遵循结构化规则
 * 不建议直接使用简单协程
 */
suspend fun structuralConcurrency() {
    log("start")
    coroutineScope {
        launch {
            delay(2000)
            log("launch")
        }
        launch {
            delay(3000)
            log("async")
        }
        log("coroutine")
    }
    //会在所有协程执行完毕后才会输出(3s后)
    log("end")
}

suspend fun cancelJob() {
    coroutineScope {
        val job1 = launch {
            while (true) {
                try {
                    delay(1000)
                    log("job1")
                } catch (e: Exception) {
                    log("job1:${e.message}")
                    //如果捕获了CancellationException异常，需要重新抛出，否则无法取消
                    if (e is CancellationException) throw e
                }
            }
        }
        val job2 = launch {
            while (true) {
                try {
                    delay(1000)
                    log("job2")
                } catch (e: Exception) {
                    log("job2:${e.message}")
                    if (e is CancellationException) throw e
                }
            }
        }
        val job3 = launch {
            delay(1000)
            log("job3")
        }
        delay(500)
        log("canceled parent")
        this.cancel()
    }
    log("end")
}