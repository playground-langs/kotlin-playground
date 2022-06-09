package oop.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class MappedPoliteString(private val datasource: MutableMap<String, Any>) : ReadWriteProperty<Any?, String> {
    override operator fun getValue(thisRef: Any?, property: KProperty<*>) =
        (datasource[property.name] as? String)?.replace("stupid", "s*****") ?: ""

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        datasource[property.name] = value
    }
}

class PostComment(private val datasource: MutableMap<String, Any>) {
    private val title: String by datasource
    private val likes: Int by datasource

    //MappedPoliteString方法中的thisRef为当前PostComment示例
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
    val b = ModelB()
    b.load()
    val list = (b.readOnlyList as MutableList)
    list.add("world")
    println(list)
    println(b.readOnlyList::class)
}

/**
 * 两个属相之间通过属性引用进行委托
 * 1.版本升级的兼容性
 * 2.可见性控制
 */
//版本升级兼容性控制
class ModelA {
    var v1: String = ""
    var v2: String by ::v1
}

//可见性控制
class ModelB {
    //对于简单类型的可见性处理
    var readOnly: Int = 0
        private set

    //对于复合类型如Map
    //这种写法无法控制外部的读写
    val data1: MutableList<String> = mutableListOf()

    //这种写法可以一定程度上控制外部不可写但可以强转来写 而且内部也无法方便的写 不推荐
    val data2: List<String> = mutableListOf()

    //解决方案 1.对外只暴露只读List(缺点:可通过强转获取写能力) 2.委托给内部可读可写属性 推荐
    val readOnlyList: List<String> by ::_backStore
    private val _backStore = mutableListOf<String>()

    fun load() {
        _backStore.add("hello")
    }
}