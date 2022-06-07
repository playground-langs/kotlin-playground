package fluency.extend

/**
 * 函数与方法的区别:方法带有receiver 第一个参数为receiver
 * 方法的类型 在函数类型基础上增加方法所在类型作为第一个参数
 */
fun main() {
    var t1: (A, Int) -> Unit = A::test1
    t1 = ::test3
    t1 = A::test2
    val a = A()
    a.test1(0)
    //等价于
    t1(a, 1)
}

fun test3(a: A, value: Int) {
    println("test3:$a $value")
}

class A {
    fun test1(value: Int) {
        println("test1:$value")
    }
}

fun A.test2(value: Int) {
    println("test2:$value")
}