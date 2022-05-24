package designpatterns.behavioral.observer.v2

import kotlin.properties.Delegates

/**
 * 使用kotlin委托
 */
interface StockUpdateListener {
    fun onRise(price: Int)
    fun onFall(price: Int)
}

class StockDisplay : StockUpdateListener {
    override fun onRise(price: Int) {
        println("the latest price has risen to $price")
    }

    override fun onFall(price: Int) {
        println("the latest price has fell to $price")
    }
}

class Stock {
    val listeners = mutableListOf<StockUpdateListener>()

    //此处可使用vetoable否决不合理的变化
    var price: Int by Delegates.observable(0) { _, old, new ->
        listeners.forEach {
            if (new > old) it.onRise(price) else it.onFall(price)
        }
    }
}

fun main() {
    val stock = Stock()
    val sd = StockDisplay()
    stock.listeners.add(sd)
    stock.price = 100
    stock.price = 80
}