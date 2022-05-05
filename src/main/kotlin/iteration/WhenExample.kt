package iteration

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