package oop.oo

/**
 * valueOf是根据实例名称来获取枚举对象 与Java一致
 * 可以构造enum range 使用in操作符
 */
enum class Operator(val value: String) {
    Add("+"), Sub("-"), Multi("*"), Div("/")
}

fun main() {
    println(Operator.values().toList())
    //ok
    println(Operator.valueOf("Add"))
    //error
    //println(Operator.valueOf("+"))
    val enumRange = Operator.Add..Operator.Div
    println(Operator.Sub in enumRange)
}