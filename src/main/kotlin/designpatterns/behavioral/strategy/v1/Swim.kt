package designpatterns.behavioral.strategy.v1

interface SwimStrategy {
    fun swim()
}

class Breaststroke : SwimStrategy {
    override fun swim() {
        println("I'm breaststroking")
    }
}

class Backstroke : SwimStrategy {
    override fun swim() {
        println("I'm backstroking")
    }
}

class Freestyle : SwimStrategy {
    override fun swim() {
        println("I'm freestyle")
    }
}

class Swimmer(private val strategy: SwimStrategy) {
    fun swim() {
        strategy.swim()
    }
}

fun main() {
    Swimmer(Backstroke()).swim()
}