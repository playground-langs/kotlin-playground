package oop.oo

/**
 * 推荐使用lazy委托
 */
class LateInitFiled {
    //method 1 不推荐
    var name1: String? = null

    //method 2 极其不推荐
    lateinit var name2: String

    //method 3 委托 推荐
    private val name3 by lazy {
        name3()
    }

    fun setLateName2() {
        name2 = "late name2"
    }

    fun use() {
        println(name1?.length)
        //不推荐
        if (::name2.isInitialized) {
            println(name2.length)
        }
        println(name3.length)
    }

    private fun name3() = "lazy name3"
}