package fp

/**
 * 输出偶数的2倍和
 * 非函数式
 */
fun doubleOfEven() {
    val doubleOfEven = mutableListOf<Int>()
    for (i in (1..10)) {
        if (i % 2 == 0) {
            doubleOfEven.add(i * 2)
        }
    }
    println(doubleOfEven)
}

/**
 * 输出偶数的2倍和
 * 函数式
 */
fun doubleOfEvenFp() {
    (1..10).filter { it % 2 == 0 }.map { it * 2 }.forEach(::println)
}

/**
 * ### lambda作为参数
 */
/**
 * 无需声明一个接口 直接声明lambda
 * 尽量让lambda作为最后一个参数
 */
fun <T> consume(t: T, action: (T) -> Unit) {
    action(t)
    //等价于
    //action.invoke(t)
}

/**
 * 演示专用
 */
fun consumeTwo(first: Int, second: Double, action1: (Int) -> Unit, action2: (Double) -> Unit) {
    action1(first)
    action2.invoke(second)
}

/**
 * 1.如果lambda作为最后一个参数可以放在()外面
 * 2.如果lambda作为最后一个参数且函数仅有一个参数则可省略()
 * 3.如果lambda的参数仅有一个可省略参数声明使用隐式名称it
 */
fun testConsume() {
    consume(1, { t -> println(t) })
    consume(2) { t -> println(t) }
    consume(3) { println(it) }
}

/**
 *
 */
fun testConsumeTwo() {
    consumeTwo(1, 1.0, { println(it) }, { println(it) })
    consumeTwo(2, 2.0, { println(it) }) { println(it) }
    consumeTwo(3, 3.0, ::println, ::println)
}

/**
 * 判断质数
 */
fun isPrime(n: Int) = n > 1 && (2 until n).none { i -> n % i == 0 }

/**
 * ### lambda作为返回值 是对lambda的抽象和复用
 */

/**
 * 找出第一次出现的长度为4或5的名称
 */
val names = listOf("Pam", "Pat", "Paul", "Paula")
fun findFirstX() {
    println(names.find { it.length == 4 })
    println(names.find { it.length == 5 })
}

/**
 * 返回lambda是对lambda的抽象和复用
 */
fun predicateOfLength(length: Int): (String) -> Boolean {
    return { input: String -> input.length == length }
}

/**
 * 可轻松实现找出第一次出现的长度为x的姓名
 */
fun findFirstXFp() {
    println(names.find(predicateOfLength(4)))
    println(names.find(predicateOfLength(5)))
}

/**
 * lambda传递给变量
 * 要么仅指定lambda参数类型以便kotlin推断lambda返回类型
 * 要么仅指定变量类型，限定lambda的参数和返回类型
 * 不推荐同时指定变量类型和参数类型
 */

fun findFirstXByVar() {
    val checkLength5 = { name: String -> name.length == 5 }
    val checkLength4: (String) -> Boolean = { name -> name.length == 5 }
    println(names.find(checkLength5))
    println(names.find(checkLength4))
}

/**
 * 使用匿名函数 不推荐
 */
fun findFirstXByFunc() {
    println(names.find(fun(name: String): Boolean { return name.length == 5 }))
    println(names.find(fun(name: String) = name.length == 5))
}

