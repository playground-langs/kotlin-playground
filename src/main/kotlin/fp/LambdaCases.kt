package fp

/**
 * kotlin lambda实现为对应签名的FunctionN的实例
 * 1.纯函数实现为静态单例
 * 2.闭包实现为匿名内部类，每次调用都会创建一个实例
 */
fun main() {
    //底层实现为Function1<Int,Unit>的子类
    var codeBlock = { n: Int -> println("$n") }
    printLambda(codeBlock)
    codeBlock.invoke(1)
}

fun printLambda(lambda: Any) {
    val javaClass = lambda.javaClass
    println(javaClass)
    val methods = javaClass.methods
    //lambda比普通类多了三个方法
    //getArity invoke invoke
    methods.forEach { println(it) }
}