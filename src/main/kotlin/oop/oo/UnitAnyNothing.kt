package oop.oo

fun returnUnit1(): Unit {
    return Unit
}

fun returnUnit2(): Unit {}

fun transferAny1(any: Any): Any {
    return any
}

fun transferAny2(any: Any?): Any? {
    return any
}

fun transferAny3(any: Any?): Any? = when (any) {
    is Int -> 1
    is String -> "hello"
    is Boolean -> throw IllegalStateException()
    is Any -> null
    else -> Unit
}

fun getNothing1(): Nothing = throw IllegalStateException()

fun getNothing2(): Nothing {
    while (true) {
    }
}

fun getNothing3(): Nothing {
    while (false) {
    }
    throw IllegalStateException()
}

fun <T> getEmptyList(): List<T> = emptyList()