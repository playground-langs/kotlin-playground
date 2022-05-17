package sequence

fun testSequence() {
    (1..10).asSequence().filter { it > 20 }.take(2).toList()
}

fun main() {
    testSequence()
}