#!/usr/bin/env kotlin

import java.io.File

/**
 * kotlin script 作为独立脚本运行不能引用工程中的类和方法
 */
println("hello world")

File("../").listFiles()?.forEach(::println)
