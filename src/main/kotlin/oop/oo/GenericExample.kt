package oop.oo

/**
 * 泛型类的设计原则:consumer in,producer out
 */

/**
 * 设计一个优先级的Pair 大的在前 小的在后
 */
class PriorityPair<T : Comparable<T>>(member1: T, member2: T) {
    val first: T
    val second: T

    init {
        if (member1 >= member2) {
            first = member1
            second = member2
        } else {
            first = member2
            second = member1
        }
    }

    override fun toString(): String {
        return "PriorityPair(first=$first, second=$second)"
    }
}

fun testPriorityPair() {
    println(PriorityPair(1, 2))
    println(PriorityPair("A", "B"))
}

/**
 * 数据类
 * 类主体{}中定义的任何属性（如果存在），将不会在生成的equals()、hashCode()和toString()方法中使用。
 * 同样，也不会为它们生成componentN()方法
 */
data class Task(
    val id: Int, val name: String,
    val completed: Boolean, val assigned: Boolean
) {
    var test1: Int = 0
    val test2: Int = 10
}

fun testDataClass() {
    val t1 = Task(id = 1, name = "task1", completed = false, assigned = false)
    println(t1)
    val t2 = t1.copy(id = 2, name = "task2")
    println(t2)
    //解构
    val (id, name, _, assigned) = t1
    println("$id $name $assigned")
}


fun main() {
    testPriorityPair()
    testDataClass()
}