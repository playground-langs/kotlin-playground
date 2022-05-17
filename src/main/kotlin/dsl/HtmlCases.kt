package dsl

import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.html
import kotlinx.html.p
import kotlinx.html.stream.appendHTML

fun testHtml() {
    val html = StringBuilder().appendHTML().html {
        body {
            h1 { +"Methods' Behavior" }
            p { +"This is a sample" }
        }
    }
    println(html)
}