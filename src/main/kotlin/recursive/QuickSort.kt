package recursive

/**
 * 使用递归实现快排
 * 递归函数需要显式声明返回类型无法自动推断
 */
fun sort(numbers: List<Int>): List<Int> =
    if (numbers.isEmpty())
        numbers
    else {
        val pivot = numbers.first()
        val tail = numbers.drop(1)
        val lessOrEqual = tail.filter { e -> e <= pivot }
        val larger = tail.filter { e -> e > pivot }
        sort(lessOrEqual) + pivot + sort(larger)
    }

fun main() {
    sort(listOf(12, 5, 15, 12, 8, 19)).run(::println)
}
