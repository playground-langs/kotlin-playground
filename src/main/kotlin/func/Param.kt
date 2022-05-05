package func

/**
 * 默认参数
 */
fun greet(name: String, msg: String = "hello"): String = "$msg $name"

/**
 * 命名参数
 */
fun createPerson(name: String, age: Int = 18, height: Int, weight: Int) {
    println("$name $age $height $weight")
}

/**
 * 可变参数
 */
fun max(vararg numbers: Int): Int {
    var large = Int.MIN_VALUE
    for (number in numbers) {
        large = if (number > large) number else large
    }
    return large
}

fun main() {
    println(greet("tom"))
    println(greet("tom", "hi"))
    //一个包内函数可互相访问
    println(f1())
    //命名参数,可以无序
    createPerson(age = 10, name = "tom", weight = 50, height = 180)
    createPerson(name = "tom", weight = 50, height = 180)
    createPerson("tom", weight = 50, height = 180)
    println(max(1, 2, 3, 4, 5, 6))
    //* spread运算符 处理数组
    println(max(*intArrayOf(1, 2, 3, 4, 5, 6)))
    //处理列表
    println(max(*listOf(1, 2, 3, 4, 5, 6).toIntArray()))
}