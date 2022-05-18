package sequence

fun testSequence() {
    val sequence = (1..10).asSequence().filter { it > 5 }.map { it * 2 }
    sequence.first().run(::println)
    sequence.last().run(::println)
    sequence.sum().run(::println)
    sequence.count().run(::println)
    sequence.toList().run(::println)
    sequence.forEach { println("foreach $it") }
    sequence.drop(1).take(2).drop(1).take(1).toList().run(::println)
}

fun testMySequence() {
    println("===========mySequence============")
    val mySequence = (1..10).asMySequence().filter { it > 5 }.map { it * 2 }
    mySequence.first().run(::println)
    mySequence.last().run(::println)
    mySequence.sum().run(::println)
    mySequence.count().run(::println)
    mySequence.toList().run(::println)
    mySequence.forEach { println("foreach $it") }
    mySequence.take(2).take(1).toList().run(::println)
    mySequence.take(0).toList().run(::println)
    mySequence.take(6).take(4).toList().run(::println)
}

fun testMyDrop() {
    (1..10).asMySequence().filter { println("filter $it");it > 5 }.map { println("map $it");it * 2 }.drop(2)
        .map { println("map2 $it");it * 2 }.toList()
        .run(::println)
}