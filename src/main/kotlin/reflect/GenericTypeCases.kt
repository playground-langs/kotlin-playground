package reflect

import kotlin.reflect.KClass
import kotlin.reflect.full.allSupertypes
import kotlin.reflect.full.declaredFunctions

abstract class Super<T> {
    val typedParameter by lazy {
        this::class.allSupertypes.first {
            (it.classifier as KClass<*>).qualifiedName == "reflect.Super"
        }.arguments.first().type
    }
}

open class Sub : Super<String>() {

}

class SubSub : Sub()

fun testSuperType() {
    println(Sub().typedParameter)
    println(SubSub().typedParameter)
}

interface TestApi {
    fun getUsers(): List<UserDTO>
}

fun testReflectFunction() {
    TestApi::class.declaredFunctions.first { it.name == "getUsers" }
        .returnType.arguments.forEach {
            println(it)
        }
    //通过函数引用
    TestApi::getUsers.returnType.arguments.forEach(::println)
}

fun main() {
    testReflectFunction()
    testSuperType()
}