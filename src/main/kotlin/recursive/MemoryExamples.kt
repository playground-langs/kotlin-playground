package recursive

import recursive.Closure.fibClosure
import recursive.Closure.memoize
import kotlin.reflect.KProperty
import kotlin.system.measureTimeMillis

/**
 * 使用记忆化(缓存中间结果)可以大大提升运算速度
 */

fun fib(n: Int): Long = when (n) {
    0, 1 -> 1L
    else -> fib(n - 1) + fib(n - 2)
}

/**
 * error!!!
 * 如此使用会导致死循环:在循环记忆化,永远无法计算出结果
 * 必须使用lambda进行循环定义
 */
fun fibWithMemoize(n: Int): Long = when (n) {
    0, 1 -> 1L
    else -> ::fibWithMemoize.memoize()(n - 1) + ::fibWithMemoize.memoize()(n - 2)
}

/**
 * 使用groovy的方式 包装lambda返回带记忆化功能的闭包
 */
object Closure {

    fun <T, R> ((T) -> R).memoize(): (T) -> R {
        val original = this
        val cache = mutableMapOf<T, R>()
        return { k: T -> cache.getOrPut(k) { original(k) } }
    }

    //递归lambda必须先声明然后延迟初始化
    lateinit var fibClosure: (Int) -> Long

    init {
        fibClosure = { n: Int ->
            when (n) {
                0, 1 -> 1L
                else -> fibClosure(n - 1) + fibClosure(n - 2)
            }
        }.memoize()
    }
}

/**
 * 使用委托实现记忆化
 */
class Memoize<T, R>(val closure: (T) -> R) {
    private val cache = mutableMapOf<T, R>()

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = { k: T ->
        cache.getOrPut(k) { closure(k) }
    }
}

//委托不需要延迟初始化，使用更简洁，推荐这种用法
val fibDelegate: (Int) -> Long by Memoize<Int, Long> { n: Int ->
    when (n) {
        0, 1 -> 1L
        else -> fibDelegate(n - 1) + fibDelegate(n - 2)
    }
}


fun main() {
    println("no memoize:")
    testNoMemoize()
    println("with memoize:")
    testWithMemoize()
    println("with delegate:")
    testWithDelegate()
}

fun testNoMemoize() {
    measureTimeMillis {
        fib(40)
    }.run(::println)
    measureTimeMillis {
        fib(45)
    }.run(::println)
}

fun testWithMemoize() {
    measureTimeMillis {
        measureTimeMillis {
            fib(45)
        }.run(::println)
        measureTimeMillis {
            //直接定义记忆化lambda
            fibClosure(45)
        }.run(::println)
        measureTimeMillis {
            //将已有函数转化为记忆化lambda时只有第一次计算可以缓存，后续所有计算均无法缓存无法加速
            //原因是 fib内部调用自身时是函数调用没有记忆化
            ::fib.memoize()(45)
        }.run(::println)
        measureTimeMillis {
            //error dead loop
            //fibWithMemoize(45)
        }.run(::println)
    }
}

fun testWithDelegate() {
    measureTimeMillis {
        fibDelegate(45)
    }.run(::println)
}
