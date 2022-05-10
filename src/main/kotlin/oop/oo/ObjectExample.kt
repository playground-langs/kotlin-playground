package oop.oo

/**
 * object声明匿名对象
 */
fun drawCircle() {
    val circle = object {
        val x = 10
        val y = 20
        val radius = 30
    }
    println("circle: x:${circle.x} y:${circle.y} r:${circle.radius}")
}

/**
 * object实现接口,必须显式声明返回类型
 */
fun createRunnable(): Runnable {
    //匿名对象
    var runnable: Runnable = object : Runnable {
        override fun run() {
            TODO("Not yet implemented")
        }
    }
    //函数式接口
    runnable = Runnable { TODO() }
    return runnable
}

/**
 * 创建单例类,创建了类型同时创建了同名单例对象(采用即时加载)
 */
object Util {
    fun numberOfProcessors() = Runtime.getRuntime().availableProcessors()
}

/**
 * 函数分模块标准:
 * 简而言之，将函数放在顶层，并根据应用程序的需要使用单例来对函数做进一步的分组和模块化
 */
fun a() {}
fun b() {}
fun c() {}

object G {
    fun g1() {}
    fun g2() {}
    fun g3() {}
}

object H {
    fun h1() {}
    fun h2() {}
    fun h3() {}
}