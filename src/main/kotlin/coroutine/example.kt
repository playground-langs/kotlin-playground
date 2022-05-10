package coroutine

import kotlinx.coroutines.*

suspend fun main() {
    coroutineScope {
        launch(Dispatchers.Default) {
            delay(1000)
            println("world")
        }
        print("hello ")
        val one = async { println(1);1 }
        val two = async { println(2);2 }
        delay(5000)
        println(one.await() + two.await())
    }
    println("end")
}