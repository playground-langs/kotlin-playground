package typesafe.generic

/**
 * 实现子类型调用父类型方法时返回运行时真正子类型
 */
open class Animal(val name: String)

open class Cat(val color: String, name: String) : Animal(name)

interface SelfType<Self> {
    val self: Self
        get() = this as Self
}

open class AnimalBuilder<Self : AnimalBuilder<Self>> : SelfType<Self> {
    protected lateinit var name: String
    fun name(name: String): Self {
        this.name = name
        return self
    }

    open fun build(): Animal {
        return Animal(name)
    }
}

class CatBuilder : AnimalBuilder<CatBuilder>() {
    lateinit var color: String
    fun color(color: String): CatBuilder {
        this.color = color
        return self
    }

    override fun build(): Cat {
        return Cat(color, name)
    }
}

fun main() {
    val cat = CatBuilder().name("tom").color("white").build()
    println("${cat.name} ${cat.color}")
}