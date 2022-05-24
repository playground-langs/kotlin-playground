package designpatterns.creational.abstractfactory.v1

import designpatterns.creational.abstractfactory.v2.Acer
import designpatterns.creational.abstractfactory.v2.Asus
import designpatterns.creational.abstractfactory.v2.Computer
import designpatterns.creational.abstractfactory.v2.Dell
import designpatterns.creational.abstractfactory.v2.DellFactory

interface Computer

class Dell : Computer
class Asus : Computer
class Acer : Computer

abstract class AbstractFactory {
    abstract fun create(): Computer

    companion object {
        operator fun invoke(factory: designpatterns.creational.abstractfactory.v2.AbstractFactory): designpatterns.creational.abstractfactory.v2.AbstractFactory {
            return factory
        }
    }
}

class DellFactory : designpatterns.creational.abstractfactory.v2.AbstractFactory() {
    override fun create(): Computer = Dell()
}

class AsusFactory : designpatterns.creational.abstractfactory.v2.AbstractFactory() {
    override fun create(): Computer = Asus()
}

class AcerFactory : designpatterns.creational.abstractfactory.v2.AbstractFactory() {
    override fun create(): Computer = Acer()
}

fun main() {
    val computer = AbstractFactory(DellFactory()).create()
    computer.run(::println)
}