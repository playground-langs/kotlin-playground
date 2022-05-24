package designpatterns.creational.factorymethod.v3

/**
 * 使用接口伴生对象优化掉工厂类
 */
interface Computer {
    val cpu: String

    companion object Factory {
        //使用操作符重载
        operator fun invoke(type: ComputerType): Computer {
            return when (type) {
                ComputerType.PC -> PC()
                ComputerType.Server -> Server()
            }
        }
    }
}

class PC(override val cpu: String = "Core") : Computer
class Server(override val cpu: String = "Xeon") : Computer

enum class ComputerType {
    PC, Server
}

//还可以使用扩展方法添加常用方法 避免创建Utils
fun Computer.Factory.fromCpu(cpu: String): ComputerType = when (cpu) {
    "Core" -> ComputerType.PC
    "Xeon" -> ComputerType.Server
    else -> throw IllegalArgumentException("unknown cpu type: $cpu")
}

fun main() {
    val computer = Computer(ComputerType.PC)
    //val computer = Computer.Factory(ComputerType.PC)
    computer.cpu.run(::println)
}