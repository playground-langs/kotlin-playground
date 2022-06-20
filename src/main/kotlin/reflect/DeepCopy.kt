package reflect

import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

interface DeepCopyable

fun <T : DeepCopyable> T.deepCopy(): T {
    if (!this::class.isData) return this
    val thisClass = this::class as KClass<T>
    return this::class.primaryConstructor!!.let { constructor ->
        constructor.parameters.associateWith { param ->
            thisClass.declaredMemberProperties.first {
                it.name == param.name
            }.get(this)
                ?.let {
                    (it as? DeepCopyable)?.deepCopy() ?: it
                }
        }.let(constructor::callBy)
    }
}

fun testDeepCopyable() {
    val group = Group("g1", 1)
    val user = UserDTO(1, "tom", 18, group)
    val u1 = user.copy()
    val u2 = user.deepCopy()
    println(u1)
    println(u2)
    println(u1.group === user.group)
    println(u2.group === user.group)
}

fun main() {
    testDeepCopyable()
}