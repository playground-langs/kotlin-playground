package coroutine

import kotlinx.coroutines.*

suspend fun main() {
    //结构化并发
    coroutineScope {
        launch(Dispatchers.Default) {
            yield()
            println("world")
        }
        print("hello ")
        val one = async { println(1);1 }
        val two = async { println(2);2 }
        println(one.await() + two.await())
    }
    println("end")
}