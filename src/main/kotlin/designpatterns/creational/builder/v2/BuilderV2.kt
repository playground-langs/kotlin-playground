package designpatterns.creational.builder.v2

/**
 * 使用具名参数优化,无需Builder类
 */
class Robot constructor(
    //must init
    val code: String?,
    val battery: String? = null,
    val height: Int? = null,
    val weight: Int? = null
) {
    init {
        require((height === null || height > 0) && (weight === null || weight > 0)) {
            "weight and height should greater than 0 when they are not null"
        }
    }

    override fun toString(): String {
        return "Robot(code=$code, battery=$battery, height=$height, weight=$weight)"
    }

}

fun main() {
    val robot = Robot(code = "007", weight = -10)
    robot.run(::println)
}