package oop

import kotlin.reflect.KProperty

class MappedPoliteString(private val datasource: MutableMap<String, Any>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>) =
        (datasource[property.name] as? String)?.replace("stupid", "s*****") ?: ""

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        datasource[property.name] = value
    }
}

class PostComment(private val datasource: MutableMap<String, Any>) {
    private val title: String by datasource
    private val likes: Int by datasource
    private val comment: String by MappedPoliteString(datasource)

    override fun toString(): String {
        return "PostComment(datasource=$datasource, title='$title', likes=$likes, comment='$comment')"
    }
}

fun main() {
    val comments = listOf(
        mutableMapOf<String, Any>(
            "title" to "Using Delegation",
            "likes" to 2,
            "comment" to "Keep it simple, stupid"
        ),
        mutableMapOf<String, Any>(
            "title" to "Using Inheritance",
            "likes" to 1,
            "comment" to "Prefer Delegation where possible"
        )
    )
    val post1 = PostComment(comments[0])
    val post2 = PostComment(comments[1])
    println(post1)
    println(post2)
    mutableMapOf(1 to 2)
}