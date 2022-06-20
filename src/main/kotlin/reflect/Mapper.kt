package reflect

import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/**
 * 为简便起见 不支持数据类中可空类型属性 可能有异常
 */
inline fun <reified To : Any> Map<String, Any?>.fromMap(): To {
    return To::class.primaryConstructor!!.let { constructor ->
        constructor.parameters.associateWith { param ->
            this[param.name]
        }.let(constructor::callBy)
    }
}

inline fun <reified From : Any, reified To : Any> From.convert(): To {
    return From::class.memberProperties.associate { it.name to it.get(this) }.fromMap()
}

fun testFromMap() {
    val group = Group("g1", 1)
    val userMap = mapOf("id" to 1, "name" to "jack", "age" to 18, "group" to group)
    val u1 = userMap.fromMap<UserDTO>()
    println(u1)
}

fun testConvert() {
    val u1 = UserDTO(1, "jack", 20, Group("g1", 1))
    val u2: UserVO = u1.convert()
    println(u2)
}

fun main() {
    testFromMap()
    testConvert()
}

