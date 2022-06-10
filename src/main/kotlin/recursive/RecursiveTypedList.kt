package recursive

/**
 * 实现递归数据结构 支持泛型
 * IntList [1,[2,[3,[4,nil]]]]
 */
sealed class RecursiveList<out T> {
    object Nil : RecursiveList<Nothing>() {
        override fun toString(): String {
            return "nil"
        }
    }

    data class Element<E>(val head: E, val tail: RecursiveList<E>) : RecursiveList<E>() {
        override fun toString(): String {
            return "[$head,$tail]"
        }
    }
}

/**
 * 实现解构
 */
operator fun <T> RecursiveList<T>.component1(): T? = when (this) {
    is RecursiveList.Element -> head
    RecursiveList.Nil -> null
}

operator fun <T> RecursiveList<T>.component2(): T? = when (this) {
    is RecursiveList.Element -> tail.component1()
    RecursiveList.Nil -> null
}

operator fun <T> RecursiveList<T>.component3(): T? = when (this) {
    is RecursiveList.Element -> tail.component2()
    RecursiveList.Nil -> null
}

operator fun <T> RecursiveList<T>.component4(): T? = when (this) {
    is RecursiveList.Element -> tail.component3()
    RecursiveList.Nil -> null
}

fun <T> recursiveListOf(vararg values: T): RecursiveList<T> {
    return when (values.size) {
        0 -> RecursiveList.Nil
        else -> RecursiveList.Element(values[0], recursiveListOf(*values.sliceArray(1 until values.size)))
    }
}

fun main() {
    val recursiveList = recursiveListOf(1, 2, 3, 4)
    println(recursiveList)
    val (a, b, c, d) = recursiveList
    println("$a $b $c $d")
    val rList = recursiveListOf("a", "b", "c", "d", "e")
    println(rList)
    //List可以解构五个元素
    val list = listOf(1, 2, 3, 4, 5, 6)
    val (a1, a2, _, a4, a5) = list
    println("$a1 $a2 $a4 $a5")
}