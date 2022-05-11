package oop.oo

/**
 * 极简类声明
 */
class Car

/**
 * 对属性的直接操作实际上是调用相应属性的getter和setter
 */
class Car1 public constructor(val yearOfMake: Int)

/**
 * Car1的等价简化声明
 */
class Car2(val yearOfMake: Int)

/**
 * val修饰 生成getter var 生成getter和setter 两个都不加仅仅相当于构造函数参数，不能有其他函数访问
 */
class Car3(val yearOfMake: Int, theColor: String) {
    var fuelLevel = 100

    init {
        if (yearOfMake < 2020) {
            fuelLevel = 90
        }
    }

    var color = theColor
        //getter和setter的使用方式
        get() = field
        set(value) {
            if (value.isBlank()) {
                throw IllegalArgumentException()
            }
            field = value
        }

    //若get set均未使用field则不会生成相应字段
    var noBackend
        get() = ""
        set(value) {

        }
}

/**
 * 二级构造函数,不能定义属性，故不能加val或var
 */
class Worker(val first: String, val last: String) {
    var fullTime = true
    var location = "-"

    constructor(first: String, last: String, fullTime: Boolean) : this(first, last) {
        this.fullTime = fullTime
    }

    constructor(first: String, last: String, fullTime: Boolean, location: String) : this(first, last, fullTime) {
        this.location = location
    }
}

/**
 * 内联类
 */
@JvmInline
value class HASH(val hash: String) {

}

/**
 * 具有内联类参数的函数会编译为带随机后缀的函数，使用@JvmName注解指定生成方法名以便java调用kotlin
 */
@JvmName("printHash")
fun printHash(input: HASH) {
    println(input)
}

fun main() {
    printHash(HASH("test"))
    val m = MachineOperator("tom")
    val singleton = MachineOperator
    //error
    //val r1: Runnable = m
    val r2: Runnable = MachineOperator
    //伴生对象可以定义显式名称，无显式名称时使用Companion
    val r3: Runnable = MachineOperator.Companion
}

/**
 * 伴生对象-类的单例伙伴 java中类的静态属性定义在伴生对象里
 * 伴生对象可以继承类实现接口,伴生对象的父类和父接口与对应类无关
 */
class MachineOperator(val name: String) {
    fun checkin() = checkedin++
    fun checkout() = checkedin--

    companion object : Runnable {
        var checkedin = 0
        fun minimumBreak() = "15 minutes every 2 hours"
        override fun run() {
            TODO("Not yet implemented")
        }
    }
}


/**
 * 伴生对象实现工厂方法模式
 */
interface Product {
    companion object Factory {
        /**
         * 使用操作符重载以便使用()进行调用
         */
        operator fun invoke(type: ProductType) = when (type) {
            ProductType.A -> A()
            ProductType.B -> B()
            ProductType.C -> C()
        }
    }
}

enum class ProductType {
    A, B, C
}

class A : Product
class B : Product
class C : Product

fun testProduct() {
    val p = Product(ProductType.A)
}

