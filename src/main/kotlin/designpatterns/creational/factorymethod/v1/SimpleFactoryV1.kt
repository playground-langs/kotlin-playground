package designpatterns.creational.factorymethod.v1

interface Computer {
    val cpu: String
}

class PC(override val cpu: String = "Core") : Computer
class Server(override val cpu: String = "Xeon") : Computer

enum class ComputerType {
    PC, Server
}

class ComputerFactory {
    fun create(type: ComputerType): Computer {
        return when (type) {
            ComputerType.PC -> PC()
            ComputerType.Server -> Server()
        }
    }
}

fun main() {
    val computer = ComputerFactory().create(ComputerType.PC)
    computer.cpu.run(::println)
}