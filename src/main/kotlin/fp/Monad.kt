package fp

import java.io.ByteArrayInputStream
import java.io.FileNotFoundException
import java.io.InputStream

/**
 * 单子模式：单子是一个泛型类型H<T>。对于该类型，我们有一个函数（如unit()）可接受类型T的一个值并返回类型H<T>的一个值。
 * 还有一个函数（如bind()）可接受类型H<T>的一个值和一个从T到H<U>的函数，并返回类型H<U>的一个值。
 */
/**
 * 可以更优雅的处理返回Either<A,B>的情况 是处理异常的一种方式
 */
fun useMonadToHandleContent(path: String): Result<Int> {
    return bind(bind(openFile(path), ::readFile), ::count)
}

fun handleContent(path: String): Result<Int> {
    val r1 = openFile(path)
    if (r1.isFailure) return Result.failure(r1.exceptionOrNull()!!)
    val r2 = readFile(r1.getOrNull()!!)
    if (r2.isFailure) return Result.failure(r2.exceptionOrNull()!!)
    val content = r2.getOrNull()!!
    return Result.success(content.length)
}

fun <T, U> bind(value: Result<T>, transform: (T) -> Result<U>): Result<U> {
    if (value.isFailure) return Result.failure(value.exceptionOrNull()!!)
    return transform(value.getOrNull()!!)
}

fun openFile(path: String): Result<InputStream> {
    return if (path == "not exist")
        Result.failure(FileNotFoundException())
    else
        Result.success(ByteArrayInputStream(path.toByteArray()))
}

fun readFile(input: InputStream): Result<String> {
    return Result.success(input.toString())
}

fun count(content: String): Result<Int> = Result.success(content.length)