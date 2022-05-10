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