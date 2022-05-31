package coroutine

import java.time.LocalDateTime

fun log(msg: String) {
    println("[${Thread.currentThread().name} ${LocalDateTime.now()}] $msg")
}