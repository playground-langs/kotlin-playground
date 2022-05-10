package typesafe.generic

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
    //协变 之后a不允许调用任何写方法，只读
    val a: Array<out Fruit> = Array<Banana>(1) { Banana() }
    //逆变 之后可写,但只能读出Any?不能确定具体类型
    val b: Array<in Fruit> = Array<Any>(1) { Any() }
    //<*> 相当于 <out Any?> 不能添加,获取出来的类型是Any?
    val list: MutableList<*> = mutableListOf(1, "test")
    //list.add(2, 1)
    val any: Any? = list[0]
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
 * in 声明逆变 可以写入，读出类型为Any?
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

/**
 * 单个约束可以放在冒号之后
 */
fun <T : AutoCloseable> close(input: T) {
    input.close()
}

/**
 * 多个约束需要使用where
 */
fun <T> appendAndClose(input: T)
        where T : AutoCloseable,
              T : Appendable {
    input.append("here")
    input.close()
}

/**
 * 星投影 只读不可写 表示任何类型
 */
fun print(array: Array<*>) {
    for (e in array) {
        println(e)
    }
    //error
    //array.set()
}

abstract class Book(val name: String)
class Fiction(name: String) : Book(name)
class NonFiction(name: String) : Book(name)

/**
 * 实现获取列表中指定类型的第一个实例
 * 实现方式不够简洁直接，较为繁琐
 */
fun <T> findFirstType(books: List<Book>, ofClass: Class<T>): T {
    val selected = books.filter { book -> ofClass.isInstance(book) }
    if (selected.isEmpty()) {
        throw RuntimeException("not found")
    }
    return ofClass.cast(selected[0])
}

val books = listOf(Fiction("Moby Dick"), NonFiction("learn to code"), Fiction("LOTR"))
fun testFindFirstType() {
    findFirstType(books, Fiction::class.java)
}

/**
 * 内联具体化类型 由编译器在调用处内联替换
 * 实现更为简洁直观
 */
inline fun <reified T> findFirst(books: List<Book>): T {
    val selected = books.filter { book -> book is T }
    if (selected.isEmpty()) {
        throw RuntimeException("not found")
    }
    return selected[0] as T
}

fun testFindFirst() {
    findFirst<Fiction>(books)
}


open class Plate<T>

class A : Plate<Int>()
class B : Plate<String>()

/**
 * 泛型类必须声明泛型 与java不同(带泛型的类型是对应不带泛型的类型的子类型:Plate<X> <: Plate)
 * kotlin中无法使用不带泛型的Plate
 */
fun test() {
    val p1: Plate<Int> = A()
    //error
    //val p: Plate = Plate<Int>()
    val p2 = Plate<Boolean>()
    val a: A = A()

}

