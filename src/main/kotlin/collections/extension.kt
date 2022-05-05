package collections


fun main() {
    iterate()
    tuple()
    array()
    list()
    set()
    map()
}

fun iterate() {
    val names = listOf("Tom", "Jerry")
    for ((index, value) in names.withIndex()) {
        println("$index:$value")
    }
}

/**
 * pair or triple
 */
fun tuple() {
    var pair = Pair("Tom", "Jerry")
    pair = "Tom" to "Jerry"
    println(pair)
    println((1 to "A").javaClass)
    //通过pair构造map
    val map = mapOf(1 to "a", 2 to "b")
    println(map)
    val circle = Triple(100, 100, 50.0)
    println(circle)
}

/**
 * array
 */
fun array() {
    //装箱后的Integer[]
    val array = arrayOf(1, 2, 3, 4)
    println(array::class)
    println(array.javaClass)
    val intArray = intArrayOf(1, 2, 3, 4, 5)
    println(intArray.javaClass)
    println(Array(5) { i ->
        (i + 1) * (i + 1)
    }.toList())
}

/**
 * list
 */
fun list() {
    //默认不可变
    val immutableList = listOf(1, 2, 3, 5)
    println("${immutableList.get(0)} $immutableList[1]")
    println(2 in immutableList)
    println(immutableList.javaClass) //java.util.Arrays$ArrayList
    //+或-运算符创建新的list
    val list1 = immutableList + 4
    val list2 = list1 - 4
    println(list2)
    val list3 = mutableListOf(1, 2, 3, 4)
    list3.add(5)

    val list4 = arrayListOf(1, 2, 3, 4)
    println(list3.javaClass) //java.util.ArrayList
    println(list4.javaClass) //java.util.ArrayList
}

/**
 * set
 */
fun set() {
    val set1 = setOf(1, 2, 3, 4)
    println(set1.javaClass)
    val set2 = hashSetOf(1, 2, 3, 4)
    println(set2.javaClass)
    val set3 = linkedSetOf(1, 2, 3, 4)
    println(set3.javaClass)
    val set4 = sortedSetOf(1, 2, 3, 4)
    println(set4.javaClass)
    println(set4 + 5 - 1)
    val set5 = mutableSetOf(1, 2, 3, 4)
    set5.add(5)
    println(set5)
}

/**
 * map
 */
fun map() {
    val map1 = mapOf(1 to "a", 2 to "b")
    println(map1.javaClass)
    val map2 = hashMapOf("a" to "b")
    println(map2.javaClass)
    val map3 = map2 + ("c" to "d") + ("e" to "f")
    println(map3)
    val value: String? = map3["e"]
    println(value)
    val map4 = map3 - "e"
    println(map4)

    for (entry in map3) {
        println("${entry.key}:${entry.value}")
    }
    for ((k, v) in map3) {
        println("$k:$v")
    }
}