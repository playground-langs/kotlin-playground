package reflect

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

/**
 * 可释放引用的不可空类型
 */
class ReleasableNotNull<T : Any> : ReadWriteProperty<Any, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("not initialized or already released")
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
    }

    fun isInitialized() = value != null

    fun release() {
        value = null
    }
}

fun <T : Any> releaseNotNull() = ReleasableNotNull<T>()

/**
 * 属性的扩展属性和扩展方法
 */
inline val KProperty0<*>.isInitialized: Boolean
    get() {
        isAccessible = true
        return (this.getDelegate() as? ReleasableNotNull<*>)?.isInitialized()
            ?: throw IllegalAccessException("delegate is not instance of ReleasableNotNull or null")
    }

fun KProperty0<*>.release() {
    isAccessible = true
    (this.getDelegate() as? ReleasableNotNull<*>)?.release()
        ?: throw IllegalAccessException("delegate is not instance of ReleasableNotNull or null")
}

class Outer {
    private var heavyObject by releaseNotNull<Array<Byte>>()

    fun onCreate() {
        println(::heavyObject.isInitialized)
        heavyObject = Array(1000) { 0 }
        println(::heavyObject.isInitialized)
    }

    fun onDestroy() {
        println(::heavyObject.isInitialized)
        ::heavyObject.release()
        println(::heavyObject.isInitialized)
    }
}

fun testReleaseNotNull() {
    val outer = Outer()
    outer.onCreate()
    outer.onDestroy()
}

fun main() {
    testReleaseNotNull()
}