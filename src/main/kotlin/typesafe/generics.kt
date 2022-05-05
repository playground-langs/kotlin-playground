package typesafe

fun main() {
    val bananaArray = arrayOf(Banana(), Banana(), Banana())
    val bananaList = listOf(Banana(), Banana(), Banana())
    //receiveFruits(bananaArray) error
    //bananaList不可变，不能添加，允许协变
    receiveFruits(bananaList)

    val basket1 = Array<Fruit>(3) { _ -> Fruit() }
    val basket2 = Array<Fruit>(3) { _ -> Fruit() }
    copyFromTo(basket1, basket2)
    val bananaBasket = Array<Banana>(3) { _ -> Banana() }
    copyFromTo(bananaBasket, basket1)
}

open class Fruit

class Banana : Fruit()
class Orange : Fruit()

fun receiveFruits(fruit: Array<Fruit>) {
    println("number of fruits:${fruit.size}")
}

fun receiveFruits(fruit: List<Fruit>) {
    println("number of fruits:${fruit.size}")
}

/**
 * out传递类型的协变，只允许读取,不允许任何其他方法调用
 */
fun copyFromTo(from: Array<out Fruit>, to: Array<in Fruit>) {
    for (i in from.indices) {
        to[i] = from[i]
        //error
        //from.set(1, Orange())
        //from[1] = Orange()
        //only can get type Any?
        val f = to[i]
    }
}