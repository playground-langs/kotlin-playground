package designpatterns.creational.factorymethod.v2

/**
 * 使用kotlin伴生对象优化
 */
interface Computer {
    val cpu: String
}

class PC(override val cpu: String = "Core") : Computer
class Server(override val cpu: String = "Xeon") : Computer

enum class ComputerType {
    PC, Server
}

object ComputerFactory {
    //使用操作符重载
    operator fun invoke(type: ComputerType): Computer {
        return when (type) {
            ComputerType.PC -> PC()
            ComputerType.Server -> Server()
        }
    }
}

fun main() {
    val computer = ComputerFactory(ComputerType.PC)
    computer.cpu.run(::println)
}