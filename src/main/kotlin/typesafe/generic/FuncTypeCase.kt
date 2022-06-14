package typesafe.generic

open class Shape {
    fun type() = "shape"
}

class Triangle : Shape() {
    fun lines() = 3
}

/**
 * 子类型看的是替换关系,比子类关系更广
 * 子类看的是继承与实现关系
 */
/**
 * kotlin中函数类型为FunctionN 其参数逆变 返回值协变 是由适用场景决定的
 */
fun main() {
    //返回值协变
    //producer使用场景是获取生产的数据并执行各种操作 因此产生子类的函数一定可以替换产生父类的函数而保证类型转换安全
    val producer1: () -> Shape = ::produceShape
    val producer2: () -> Shape = ::produceTriangle
    val producer3: () -> Triangle = ::produceTriangle
    //error
    //val producer4: () -> Triangle = ::produceShape
    //参数逆变
    //consumer使用场景为获取传入数据并执行各种对应类型操作 由于入参的实参有各种类型 因此形参必须为更抽象的类型才能满足入参的要求
    val consumer1: (Triangle) -> Unit = ::consumeTriangle
    val consumer2: (Triangle) -> Unit = ::consumeShape
    val consumer3: (Shape) -> Unit = ::consumeShape

    //error
    //val consumer4: (Shape) -> Unit = ::consumeTriangle

    val shape = Triangle()
    val container: Container<Shape> = Container(shape)
    container.contains(shape).run(::println)
}

fun produceShape(): Shape {
    return Shape()
}

fun produceTriangle(): Triangle {
    return Triangle()
}

fun consumeShape(shape: Shape) {
    println(shape)
}

fun consumeTriangle(triangle: Triangle) {
    println(triangle)
}

/**
 * 逆变使用场景
 * 由于shape为非泛型 可以传入子类型 因此consumer必须可以包含Shape及其子类才能替换 因此属于逆变
 */
fun useShapeAsConsumer(shape: Shape, consumer: (Shape) -> Unit) {
    consumer(shape)
}

/**
 * 协变使用场景
 * 接收者为底层类型 因此producer返回值可以为底层类型及其子类型才能替换 因此属于协变
 */
fun useShapeAsProducer(producer: () -> Shape) {
    val shape: Shape = producer()
}

/**
 * 参数可以逆变的情形是:在方法内部不发生写操作
 */
class Container<out T : Shape>(val value: T) {

    fun contains(e: @UnsafeVariance T): Boolean {
        println(e.type())
        return e == value
    }
}






