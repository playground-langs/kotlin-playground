package anno

import java.lang.reflect.Proxy

/**
 * 注解属性类型 受限类型 编译器就可以确定的类型 不能使用自定义类型
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Api(val value: String)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Get(val value: String)

@Api("https://example")
interface Example {
    @Get("/test")
    fun test(): String

    @Api("/users")
    interface User {
        @Get("/test1")
        fun test1(): String

        @Get("/test2")
        fun test2(): String
    }

    @Api("/orders")
    interface Order {
        @Get("/test1")
        fun test1(): String

        @Get("/test2")
        fun test2(): String
    }
}

/**
 * 仿Retrofit实现多级嵌套api注解解析
 */
object RetroApi {

    /**
     * 递归获取外部类
     */
    val enclosing = { clazz: Class<*> ->
        sequence {
            var current: Class<*>? = clazz
            while (current != null) {
                current = current.also { yield(it) }.enclosingClass
            }
        }
    }

    inline fun <reified T> printEnclosingList() {
        enclosing(T::class.java).toList().run(::println)
    }

    inline fun <reified T> create(): T {
        return Proxy.newProxyInstance(RetroApi.javaClass.classLoader, arrayOf(T::class.java)) { proxy, method, args ->
            val interfaces = enclosing(T::class.java).takeWhile { it.isInterface }.toList()
            val path = interfaces.foldRight(StringBuilder()) { clazz, acc ->
                acc.append(clazz.getAnnotation(Api::class.java)?.value ?: "")
            }.toString()
            val funcUrl = method.getAnnotation(Get::class.java)?.value
            println("${method.name} url is $path$funcUrl")
            T::class.simpleName + "." + method.name
        } as T
    }
}

fun main() {
    RetroApi.printEnclosingList<Example>()
    RetroApi.printEnclosingList<Example.User>()
    RetroApi.printEnclosingList<Example.Order>()
    RetroApi.create<Example>().test().run(::println)
    RetroApi.create<Example.User>().test1().run(::println)
    RetroApi.create<Example.User>().test2().run(::println)
    RetroApi.create<Example.Order>().test1().run(::println)
    RetroApi.create<Example.Order>().test2().run(::println)
}