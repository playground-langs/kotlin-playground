package recursive

/**
 * 实现递归数据结构
 * IntList [1,[2,[3,[4,nil]]]]
 */
sealed class IntList {
    object Nil : IntList() {
        override fun toString(): String {
            return "nil"
        }
    }

    data class Cons(val head: Int, val tail: IntList) : IntList() {
        override fun toString(): String {
            return "[$head,$tail]"
        }
    }
}

/**
 * 实现解构
 */
operator fun IntList.component1(): Int? = when (this) {
    is IntList.Cons -> head
    IntList.Nil -> null
}

operator fun IntList.component2(): Int? = when (this) {
    is IntList.Cons -> tail.component1()
    IntList.Nil -> null
}

operator fun IntList.component3(): Int? = when (this) {
    is IntList.Cons -> tail.component2()
    IntList.Nil -> null
}

operator fun IntList.component4(): Int? = when (this) {
    is IntList.Cons -> tail.component3()
    IntList.Nil -> null
}

fun intListOf(vararg values: Int): IntList {
    return when (values.size) {
        0 -> IntList.Nil
        else -> IntList.Cons(values[0], intListOf(*values.sliceArray(1 until values.size)))
    }
}

/**
 * sum
 */
fun IntList.sum(): Int = when (this) {
    is IntList.Cons -> head + tail.sum()
    IntList.Nil -> 0
}

fun main() {
    val intList = intListOf(1, 2, 3, 4)
    println(intList)
    val (a, b, c, d) = intList
    println("$a $b $c $d")
    println("sum of IntList:${intList.sum()}")
    //List可以解构五个元素
    val list = listOf(1, 2, 3, 4, 5, 6)
    val (a1, a2, _, a4, a5) = list
    println("$a1 $a2 $a4 $a5")
}