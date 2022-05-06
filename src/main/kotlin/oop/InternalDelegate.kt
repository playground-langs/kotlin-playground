package oop

import kotlin.properties.Delegates.observable
import kotlin.properties.Delegates.vetoable

fun getTemperature(city: String): Double {
    println("fetch from remote service for $city")
    return 30.0
}

fun main() {
    println("eager usage:")
    useTemp(false)
    useTemp(true)
    println("lazy usage:")
    useTempLazy(false)
    useTempLazy(true)
    println("observable usage:")
    useObservable()
    println("vetoable usage:")
    useVetoable()
}

fun useTemp(show: Boolean) {
    val temperature = getTemperature("test")
    if (show && temperature > 20) {
        println("warm")
    } else {
        println("nothing to report")
    }
}

/**
 * lazy委托变量会延迟计算且仅会被计算一次
 */
fun useTempLazy(show: Boolean) {
    val temperature by lazy { getTemperature("test") }
    if (show && temperature > 20) {
        println("warm")
    } else {
        println("nothing to report")
    }
    println("get lazy again: $temperature")
}

fun useObservable() {
    var count by observable(0) { property, oldValue, newValue ->
        println("Property:$property old:$oldValue new: $newValue")
    }
    printValue(count)
    count++
    printValue(count)
    count--
    printValue(count)
}

fun <T> printValue(value: T) {
    println("the value is $value")
}

fun useVetoable() {
    var count by vetoable(0) { _, oldValue, newValue -> newValue > oldValue }
    printValue(count)
    count++
    printValue(count)
    count--
    printValue(count)
}