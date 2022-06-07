package oop.oo

/**
 * 类内部定义带有receiver的扩展函数:
 * 可以获取两个receiver
 * 类内部扩展函数只有在this为外部类时才能调用,不能在顶层直接调用
 * 可以在类内部或使用with等构造this作用域的函数进行调用
 */
class A1 {
    fun A2.ext2() {
        //this is A2
        println("this is ${this.javaClass}")
        //outer this is A1
        println("outer this is ${this@A1.javaClass}")
    }

    fun call() {
        A2().ext2()
    }
}

class A2

//外部扩展函数:定义一个receiver
fun A2.ext1() {
    //this is A2
    println("this is ${this.javaClass}")
}

fun main() {
    val a2 = A2()
    val a1 = A1()
    a2.ext1()
    //1.call ext2 use with
    with(a1) {
        a2.ext2()
    }
    //2.call ext2 use outer method
    a1.call()
    //3.call ext2 in this==A1 scope
    a1.run {
        a2.ext2()
    }
}

