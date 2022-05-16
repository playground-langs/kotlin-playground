package fluency.extend

/**
 * 在类内部使用扩展 仅限于类内部使用 其他地方不可见 提供隔离
 */
class PointX(x: Int, y: Int) {
    private val pair = Pair(x, y)

    private val firstSign = if (pair.first < 0) "" else "+"
    private val secondSign = if (pair.second < 0) "" else "+"

    override fun toString(): String = pair.pointToString()

    /**
     * 在类中创建的扩展函数有两个接收者
     * 扩展接收者:Pair
     * 分发接收者:PointX
     * 扩展函数中用到的属性会先在扩展接收者中查找 若找不到则再到分发接收者中找
     * 看起来像是拥有两个this
     */
    private fun <A, B> Pair<A, B>.pointToString() =
        "${firstSign}${first},${this@PointX.secondSign}${this.second}"
}
