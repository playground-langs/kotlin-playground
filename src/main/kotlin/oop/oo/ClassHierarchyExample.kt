package oop.oo

interface Remote {
    //抽象属性
    val name: String

    var test: Int

    //抽象方法
    fun up()
    fun down()

    /**
     * 默认实现中可以使用接口中定义的属性
     */
    fun doubleUp() {
        println("$name $test")
        up()
        up()
    }

    /**
     * 接口可以有伴生对象实现类似java的静态方法
     */
    companion object {
        fun combine(first: Remote, second: Remote) = object : Remote {
            override val name: String = "unknown"
            override var test: Int = 0

            override fun up() {
                first.up()
                second.up()
            }

            override fun down() {
                first.down()
                second.down()
            }
        }
    }
}

class TV(var volume: Int = 0)

class TVRemote(val tv: TV) : Remote {
    override val name: String
        get() = "TV"
    override var test: Int = 0

    override fun up() {
        tv.volume++
    }

    override fun down() {
        tv.volume--
    }
}

/**
 * 抽象类
 */
abstract class Musician(val name: String, val activeFrom: Int) {
    abstract fun instrumentType(): String
    fun test() {}
}

class Cellist(name: String, activeFrom: Int) : Musician(name, activeFrom) {
    override fun instrumentType(): String = "String"
}

/**
 * 嵌套类和内部类
 */
class AA {
    private var aa = 0
    var bb = 0

    //嵌套类相当于java中的static声明的类
    class BB {
        fun test() {
            //error
            //println("$aa $bb")
        }
    }

    //相当于java中的内部类
    inner class CC {
        var aa = 0
        var bb = 0
        fun test() {
            //可使用this@className引用外部同名属性
            println("$aa $bb ${this@AA.aa} ${this@AA.bb}")
        }
    }
}

/**
 * 类的继承体系
 * 类的方法和属性都可重写 默认不可重写
 */
open class Vehicle(val year: Int, open var color: String) {
    open val km = 0
    override fun toString(): String {
        return "Vehicle(year=$year, color='$color', km=$km)"
    }

    fun repaint(newColor: String) {
        color = newColor
    }
}

class CarX(year: Int, color: String) : Vehicle(year, color) {
    //val,var可以重写为var var不能重写为val
    override var km: Int = 0
        set(value) {
            if (value < 0) {
                throw IllegalArgumentException()
            } else {
                field = value
            }
        }

    fun drive(distance: Int) {
        km += distance
    }
}

/**
 * 密封类 类似于rust中的枚举 有限直接子类的无限实例 类型可数 值不可数
 * 同一个包内可继承 构造器可为private或protected
 * 子类可选择是否可被继承 使用when时保证直接子级穷尽即可
 */
sealed class Card protected constructor(val suit: Suit)

open class Ace(suit: Suit) : Card(suit)
class King(suit: Suit) : Card(suit)
class Queen(suit: Suit) : Card(suit)
class Jack(suit: Suit) : Card(suit)

class XX(suit: Suit) : Ace(suit)

class Pip(suit: Suit, val number: Int) : Card(suit) {
    init {
        if (number < 2 || number > 10) {
            throw IllegalArgumentException("Pip has to be between 2 and 10")
        }
    }
}

/**
 * 枚举类 同一个类的有限实例
 */
enum class Suit(val symbol: Char) {
    CLUBS('\u2663'),
    DIAMONDS('\u2666'),
    HEARTS('\u2665') {
        override fun display() = "${super.display()} $symbol"
    },
    SPADES('\u2660');

    open fun display() = "$symbol $name ${this.ordinal} ${this.javaClass}"
    override fun toString(): String {
        return display()
    }
}

/**
 * 穷尽分支
 * sealed类子类型可穷尽
 */
fun processSealedCard(card: Card) = when (card) {
    is Ace -> card.suit
    is King,
    is Queen,
    is Jack -> "$card"
    is Pip -> "${card.number} ${card.suit}"
}

/**
 * 此种写法必须要else 没有参数的when语句条件不可数无法穷尽
 */
fun processEnumsWrong(suit: Suit) = when {
    suit === Suit.CLUBS -> suit.name
    suit === Suit.DIAMONDS -> suit.name
    suit === Suit.HEARTS -> suit.name
    suit === Suit.SPADES -> suit.name
    else -> "unknown"
}

/**
 * 可穷举Suit实例的范围
 */
fun processEnums(suit: Suit) = when (suit) {
    Suit.CLUBS -> suit.name
    Suit.DIAMONDS -> suit.name
    Suit.HEARTS -> suit.name
    Suit.SPADES -> suit.name
}


fun main() {
    println(Suit.CLUBS)
    println(Suit.DIAMONDS)
    println(Suit.HEARTS)
    println(Suit.SPADES)
}
