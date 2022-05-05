package func

fun f1() = 2

//error 等号与{}连用在声明明确返回值时会报错,在不声明时会返回函数
//fun f22():Int = { 2 }
fun f2() = { 2 } // == {()->{2}}


fun f3(factor: Int) = { n: Int -> n * factor }

fun main() {
    println(f1())
    println(f2())
    println(f2()())
    println(f3(2)(3))
}