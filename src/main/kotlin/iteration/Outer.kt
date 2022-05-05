package iteration

fun main() {
    val oneToFive = 1..5
    println(oneToFive.toList())
    val aToZ = 'a'..'z'
    println(aToZ.toList())
    val seekHelp = "hell".."help"
    println(seekHelp.contains("helm"))
}

/**
 * 正向迭代
 */
fun f1() {
    for (i in 1..5) {
        println(i)
    }
    for (ch in 'a'..'z') {
        println(ch)
    }
}

/**
 * 反向迭代
 */
fun f2() {
    for (i in 5.downTo(1)) {
        println(i)
    }
    for (i in 5 downTo 1) {
        println(i)
    }
}

/**
 * 跳过某些值
 */
fun f3() {
    for (i in 1 until 5) {
        println(i)
    }
    for (i in 1..10 step 3) {
        println(i)
    }
    for (i in (1..10).filter { it % 3 == 0 || it % 5 == 0 }) {
        println(i)
    }
}

/**
 * 遍历集合
 */
fun f4() {
    val array = arrayOf(1, 2, 3, 4)
    for (i in array) {
        println(i)
    }
    val list = listOf(1, 2, 3, 4)
    for (e in list) {
        println(e)
    }
}

/**
 * 获取索引
 */
fun f5() {
    val array = arrayOf(1, 2, 3, 4)
    for (index in array.indices) {
        println("$index, ${array[index]}")
    }
    val list = listOf(1, 2, 3, 4)
    for ((index, e) in list.withIndex()) {
        println("$index, $e")
    }
}
