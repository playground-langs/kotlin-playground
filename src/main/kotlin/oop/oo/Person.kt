package oop.oo

data class Person(val name: String, val age: Int? = null)

fun main() {
    val persons = listOf(Person("Alice"), Person("Bob", 29), Person("Tom", age = 30))
    val oldest = persons.maxByOrNull { it.age ?: 0 }
    println("the oldest is $oldest")
}
