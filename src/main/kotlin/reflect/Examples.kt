package reflect

import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.typeOf

/**
 * kotlin的泛型信息存储于metadata注解中
 * KType描述未擦除的类型
 */
fun main() {
    val kls: KClass<String> = String::class

    println("String properties:")
    kls.declaredMemberProperties.forEach { println(it.name) }

    val mapKls = Map::class
    println(mapKls)

    val mapType = typeOf<Map<String, Int>>()
    //kotlin反射可以获取运行时类型
    println(mapType)

    println("String methods:")
    kls.members.forEach {
        println(it.name)
    }
}