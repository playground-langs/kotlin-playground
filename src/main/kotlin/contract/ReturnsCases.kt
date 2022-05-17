package contract

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * 辅助智能推断
 */
data class MyEvent(val message: String)

/**
 * 如果不使用contract标注 kotlin无法使用间接推断的结论
 */
/**
 * case 1
 */
@ExperimentalContracts
fun processMyEvent(event: Any?) {
    if (event is MyEvent) {
        println(event.message)
    }
    //此时可以获取间接推断标注的结论
    if (isMyEvent(event)) {
        println(event.message)
    }
}

@ExperimentalContracts
fun isMyEvent(event: Any?): Boolean {
    contract {
        returns(true) implies (event is MyEvent)
    }
    return event is MyEvent
}

/**
 * case 2
 */
@OptIn(ExperimentalContracts::class)
fun String?.hasText(): Boolean {
    contract {
        returns(true) implies (this@hasText != null)
    }
    return this != null && this.isNotEmpty()
}

fun printLength(s: String?) {
    if (s.hasText()) {
        println(s.length)
    }
}
