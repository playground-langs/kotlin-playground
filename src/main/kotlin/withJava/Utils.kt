@file:JvmName("MyUtils")

package withJava

import java.io.File
import java.io.FileNotFoundException

/**
 * 顶级函数需要使用生成的类名来访问,默认为:${fileName}Kt
 * 声明异常才能在java中处理异常
 */
@Throws(FileNotFoundException::class)
fun readFile(path: String) = File(path).readLines()
