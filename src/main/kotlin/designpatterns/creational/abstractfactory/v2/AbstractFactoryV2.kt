package designpatterns.creational.abstractfactory.v2

interface Computer

class Dell : Computer
class Asus : Computer
class Acer : Computer

abstract class AbstractFactory {
    abstract fun create(): Computer

    companion object {
        //使用具体化泛型去掉参数
        inline operator fun <reified T : Computer> invoke(): AbstractFactory = when (T::class) {
            Dell::class -> DellFactory()
            Asus::class -> AsusFactory()
            Acer::class -> AcerFactory()
            else -> throw IllegalArgumentException()
        }
    }
}

class DellFactory : AbstractFactory() {
    override fun create(): Computer = Dell()
}

class AsusFactory : AbstractFactory() {
    override fun create(): Computer = Asus()
}

class AcerFactory : AbstractFactory() {
    override fun create(): Computer = Acer()
}

fun main() {
    val computer = AbstractFactory<Dell>().create()
    computer.run(::println)
}