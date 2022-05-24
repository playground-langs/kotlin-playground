package designpatterns.structural.decorator.v2

class Printer {
    fun drawLine() {
        println("-----")
    }

    fun drawDottedLine() {
        println(".....")
    }

    fun drawStars() {
        println("*****")
    }
}
//需求:需要在所有划线方法前后输出文字说明

//使用扩展函数
fun Printer.startDraw(decorated: Printer.() -> Unit) {
    println("start drawing")
    decorated()
    println("end drawing")
}

fun main() {
    Printer().run {
        startDraw {
            drawLine()
        }
        startDraw {
            drawDottedLine()
        }
        startDraw {
            drawStars()
        }
    }
}