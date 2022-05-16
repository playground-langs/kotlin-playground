package fluency.extend

import java.net.URL

/**
 * 无Companion对象则无法注入静态方法 java类无法注入静态方法
 */

fun String.Companion.toURL(link: String) = URL(link)

fun testStringToUrl() {
    println(String.toURL("https://www.baidu.com"))
}