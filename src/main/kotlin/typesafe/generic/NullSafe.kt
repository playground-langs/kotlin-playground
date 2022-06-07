package typesafe.generic

/**
 * 泛型空安全
 * T=>T:Any?
 * 空安全 T:Any
 */
/**
 * 泛型T=>T:Any?
 */
fun <T> saveToSet(element: T) {
    sortedSetOf(element)
}

/**
 * 等价于saveToSet
 */
fun <T : Any?> unsafeSaveToSet(element: T) {
    sortedSetOf(element)
}

fun <T : Any> safeSaveToSet(element: T) {
    sortedSetOf(element)
}


fun main() {
    //NPE
    saveToSet(null)
    //NPE
    unsafeSaveToSet(null)
    //compile error
    //safeSaveToSet(null)

    var a: Int? = null
    //?.let可快捷实现空安全逻辑
    a?.let {
        it != null
    }
}