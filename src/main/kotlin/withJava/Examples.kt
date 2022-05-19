package withJava

import com.techzealot.kt.JavaClass

/**
 * kotlin调用java基本无障碍
 */
fun testJavaMethod() {
    val javaObject = JavaClass()
    //使用名称直接访问getter
    println(javaObject.zero)
    //kotlin中的集合可以直接映射到java
    println(javaObject.convertToUpper(listOf("Jack", "Jill")))
    //可以处理无歧义的关键字方法名
    javaObject.suspend()
    //当方法名是关键字可以使用反影号转义
    println(javaObject.`when`())
}