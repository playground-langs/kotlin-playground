package designpatterns.behavioral.strategy.v2

/**
 * 使用高阶函数
 */
class Swimmer(private val swimming: () -> Unit) {
    fun swim() {
        swimming()
    }
}

fun backstroke() {
    println("I'm backstroking")
}

fun breaststroke() {
    println("I'm breaststroking")
}

fun freestyle() {
    println("I'm freestyle")
}

fun main() {
    Swimmer(::backstroke).swim()
    Swimmer(::breaststroke).swim()
    Swimmer(::freestyle).swim()
}
