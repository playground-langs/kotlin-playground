package designpatterns.behavioral.chain.v2

/**
 * 定义偏函数
 */
class PartialFunction<in P, out R>(private val definedAt: (P) -> Boolean, private val f: (P) -> R) : (P) -> R {
    override fun invoke(p1: P): R {
        if (definedAt(p1)) {
            return f(p1)
        } else {
            throw IllegalArgumentException("$p1 is not support")
        }
    }

    fun isDefinedAt(p: P) = definedAt(p)
}

//快捷函数构造调用链
infix fun <P, R> PartialFunction<P, R>.orElse(that: PartialFunction<P, R>): PartialFunction<P, R> {
    //使用||运算实现选择一个执行
    return PartialFunction({ this.isDefinedAt(it) || that.isDefinedAt(it) }) {
        when {
            this.isDefinedAt(it) -> this(it)
            else -> that(it)
        }
    }
}

data class ApplyEvent(val money: Int, val title: String)

fun main() {
    //使用run可以定义局部作用域 处理变量名冲突问题
    val groupLeader = run {
        val definedAt: (ApplyEvent) -> Boolean = { it.money <= 200 }
        val handler: (ApplyEvent) -> Unit = { println("group leader handled app:${it.title}") }
        PartialFunction(definedAt, handler)
    }

    val president = run {
        val definedAt: (ApplyEvent) -> Boolean = { it.money <= 500 }
        val handler: (ApplyEvent) -> Unit = { println("president handled app:${it.title}") }
        PartialFunction(definedAt, handler)
    }

    val college = run {
        val definedAt: (ApplyEvent) -> Boolean = { true }
        val handler: (ApplyEvent) -> Unit = {
            when {
                it.money > 1000 -> println("college:this application is refused")
                else -> println("college handled app:${it.title}")
            }
        }
        PartialFunction(definedAt, handler)
    }
    val chain = groupLeader orElse president orElse college
    chain(ApplyEvent(10, "buy a pen"))
    chain(ApplyEvent(200, "team building"))
    chain(ApplyEvent(600, "hold a match"))
    chain(ApplyEvent(1200, "meeting"))
}