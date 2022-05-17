package fluency.receiver

/**
 * 带接收方的lambda表达式
 */
/**
 * 闭包使用词法作用域
 */
var length = 100

val printIt: (Int) -> Unit = { n ->
    println("n is $n, length is $length")
}

fun testPrintIt() {
    printIt(6)
}

/**
 * 使用带接收者的lambda
 * 带接收者的lambda使用的变量会优先从接收者中寻找 找不到再到词法作用域内寻找
 */
val printItWithReceiver: String.(Int) -> Unit = { n ->
    println("n is $n, length is $length")
}

/**
 * 带接收者的lambda相当于接收者的扩展函数 底层实现即是如此
 */
fun testPrintItWithReceiver() {
    printItWithReceiver("hello", 6)
    //推荐这样调佣
    "hello".printItWithReceiver(6)
}

/**
 * 带接收方的多个作用域
 */
fun top(func: String.() -> Unit) = "hello".func()

fun nested(func: Int.() -> Unit) = (-2).func()

/**
 * 嵌套的带接收方的lambda会优先寻找自己作用域内的属性，若找不到会隐式逐级寻找父级
 * 也可以通过this@OuterClassName显式引用
 */
fun testReceiverScope() {
    top {
        println("in outer lambda this:$this,length:$length")
        nested {
            println("in inner lambda this:$this ${toDouble()}")
            println("from inner to outer property,length:$length")
            println("from inner to outer property,length:${this@top.length}")
            println("from inner to outer receiver outer-this:${this@top}")
        }
    }
}