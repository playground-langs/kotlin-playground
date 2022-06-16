package fp

/**
 * 函子是具有类型参数T的任意类型H（H<T>），并且对于该类型，
 * 有一个函数map()可接受类型H<T>的实参和一个从T到U的函数，
 * 并返回H<U>类型的一个值。
 */
interface Functor<T> {
    fun <U> map(transform: (value: T) -> U): Functor<U>
}

data class Box<T>(val value: T) : Functor<T> {
    override fun <U> map(transform: (value: T) -> U): Box<U> {
        return unit(transform(value))
    }
}

fun <T> unit(value: T) = Box(value)

/**
 * 函数的函子
 */
fun <T, U> map(f: (arg1: T, arg2: T) -> T, func: (T) -> U): (T, T) -> U {
    return { arg1: T, arg2: T ->
        func(f(arg1, arg2))
    }
}

fun add(x: Int, y: Int): Int = x + y

fun stringify(value: Int): String = value.toString()

fun useFunctionFunctor() {
    val functor = map(::add, ::stringify)
    functor(1, 2)
}