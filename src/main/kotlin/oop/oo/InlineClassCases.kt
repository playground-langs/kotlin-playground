package oop.oo

@JvmInline
value class BoxInt(val value: Int) {
    fun inc(): BoxInt = BoxInt(value + 1)
    fun dec(): BoxInt = BoxInt(value - 1)
}

enum class StateEnum {
    Idle, Busy
}

/**
 * 内联类模拟枚举降低内存开销
 */
@JvmInline
value class State private constructor(val ordinary: Int) {
    companion object {
        val Idle = State(0)
        val Busy = State(1)
    }

    fun values() = arrayOf(Idle, Busy)
}
