package oop

import kotlin.reflect.KProperty

/**
 * 参考kotlin.properties.ReadWriteProperty的方法签名
 */
class PoliteString(var content: String) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>) = content.replace("stupid", "s*****")
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        content = value
    }
}

fun beingPolite(content: String) = PoliteString(content)

fun main() {
    //局部变量委托
    var comment: String by PoliteString("some nice message")
    println(comment)
    comment = "this is stupid"
    println(comment)
    //通过函数进行委托
    val c: String by beingPolite("that is stupid")
    println(c)
}