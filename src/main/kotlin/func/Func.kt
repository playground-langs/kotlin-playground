package func

import oop.oo.Person


fun main() {
    greet()
    println(hello())
    println(greet())
}

fun greet(): Unit {
    println("hello")
}

fun hello() = "hello"

fun sum(a: Int, b: Int): Int = a + b

fun change(person: Person) {
    //person=Person("cat")
    println("param is val,can't change ${person.name}")
}