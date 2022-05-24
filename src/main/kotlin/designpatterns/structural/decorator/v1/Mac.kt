package designpatterns.structural.decorator.v1

/**
 * 使用委托实现装饰
 */
interface MacBook {
    fun getCost(): Int
    fun getDesc(): String
    fun getProdDate(): String
}

class MacBookPro : MacBook {
    override fun getCost() = 10000

    override fun getDesc(): String = "MacBook Pro"

    override fun getProdDate() = "Late 2011"
}

class ProcessorUpgradeMacBookPro(private val macBook: MacBook) : MacBook by macBook {
    override fun getCost() = macBook.getCost() + 219

    override fun getDesc() = macBook.getDesc() + ",+1G Memory"
}

fun main() {
    val macBookPro = MacBookPro()
    val pump = ProcessorUpgradeMacBookPro(macBookPro)
    println(macBookPro.getCost())
    println(pump.getDesc())
}