package iteration

import oop.oo.*

fun main() {
    println(whatToDo("Friday"))
    println(whatToDo("test"))
    printWhatToDo(2)
}

/**
 * when用作表达式,会检查是否有else
 */
fun whatToDo(dayOfWeek: Any) = when (dayOfWeek) {
    "Saturday", "Sunday" -> "Relax"
    in listOf("Monday", "Tuesday", "Wednesday", "Thursday") -> "Work hard"
    in 2..4 -> "Work hard"
    "Friday" -> "Party"
    is String -> "What?"
    else -> "No clue"
}

/**
 * when用作语句,不会检查else.
 */
fun printWhatToDo(dayOfWeek: Any) {
    when (dayOfWeek) {
        "Saturday", "Sunday" -> println("Relax")
        in listOf("Monday", "Tuesday", "Wednesday", "Thursday") -> println("Work hard")
        in 2..4 -> println("Work hard")
        "Friday" -> println("Party")
        is String -> println("What?")
    }
}

/**
 * 限定变量作用域
 */
fun systemInfo(): String =
    when (val numberOfCores = Runtime.getRuntime().availableProcessors()) {
        1 -> "1 core"
        in 2..16 -> "You have $numberOfCores cores"
        else -> "$numberOfCores cores"
    }

/**
 * 无参when必须加else
 */
fun noParamWhen(num: Int) = when {
    num in 1..100 -> 1
    num !in 1..100 -> 0
    else -> 0
}

/**
 * 除sealed is 和 enum外即使逻辑上穷尽分支也要加else
 */
fun paramWhen(num: Int) = when (num) {
    in 1..100 -> 1
    !in 1..100 -> 0
    else -> 0
}

/**
 * 需要使用as显式提示类型
 */
fun <T : Card> sealedGenericWhen(card: T) = when (card as Card) {
    is Ace -> "ace"
    is King -> "king"
    is Queen -> "queen"
    is Jack -> "jack"
    is Pip -> "pip"
}