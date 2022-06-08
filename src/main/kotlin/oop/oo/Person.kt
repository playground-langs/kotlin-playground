package oop.oo

/**
 * 数据类不能被继承 破坏了equals的对称性和传递性
 * 可以通过noarg和allopen插件使之与Javabean一致 不推荐
 */
data class Person(val name: String, val age: Int? = null)

fun main() {
    val persons = listOf(Person("Alice"), Person("Bob", 29), Person("Tom", age = 30))
    val oldest = persons.maxByOrNull { it.age ?: 0 }
    println("the oldest is $oldest")
}
