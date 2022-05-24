package designpatterns.behavioral.observer.v1

import java.util.*

class Stock : Observable() {
    val observers = mutableListOf<Observer>()

    fun setStockChanged(price: Int) {
        observers.forEach { it.update(this, price) }
    }
}

class StockDisplay : Observer {
    override fun update(o: Observable?, arg: Any?) {
        if (o is Stock) {
            println("the latest stock price is $arg")
        }
    }
}

fun main() {
    val stock = Stock()
    val sd = StockDisplay()
    stock.observers.add(sd)
    stock.setStockChanged(100)
}