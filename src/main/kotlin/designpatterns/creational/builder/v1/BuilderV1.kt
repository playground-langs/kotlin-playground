package designpatterns.creational.builder.v1

class Robot private constructor(
    val code: String?,
    val battery: String?,
    val height: Int?,
    val weight: Int?
) {

    //嵌套类
    class Builder(private val code: String) {
        private var battery: String? = null
        private var height: Int? = null
        private var weight: Int? = null
        fun battery(battery: String?): Builder {
            this.battery = battery
            return this
        }

        fun height(height: Int?): Builder {
            this.height = height
            return this
        }

        fun weight(weight: Int?): Builder {
            this.weight = weight
            return this
        }

        fun build(): Robot =
            if (height?.let { it <= 0 } == true || weight?.let { it <= 0 } == true) {
                throw IllegalArgumentException("weight and height should greater than 0 when they are not null")
            } else {
                Robot(code, battery, height, weight)
            }
    }

    override fun toString(): String {
        return "Robot(code=$code, battery=$battery, height=$height, weight=$weight)"
    }
}

fun main() {
    val robot = Robot.Builder("007")
        .battery("R6").height(100)
        .build()
    robot.run(::println)
}