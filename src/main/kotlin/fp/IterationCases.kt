package fp

/**
 * reduce实现原理 第一个元素赋值给第一个参数(累计值) 后续元素依次应用lambda返回值赋值给累计值 最终返回累计值
 * (1,2,3) 加倍并求和
 */
fun testOuterMapReduce() {
    //map reduce的外部迭代形式(1.做什么,2.怎么做)
    val iterator = listOf(1, 2, 3).listIterator()
    if (!iterator.hasNext()) {
        throw UnsupportedOperationException()
    }
    var sum = iterator.next() * 2
    while (iterator.hasNext()) {
        sum += iterator.next() * 2
    }
    println(sum)
}

/**
 * (1,2,3) 加倍并求和
 */
fun testInnerMapReduce() {
    //map reduce的内部迭代形式(做什么，不关心怎么做) 复用性更强
    (1..3).map { it * 2 }.reduce { acc, item -> acc + item }.run(::println)
}

data class Person(val name: String, val age: Int)

val people = listOf(Person("tom", 17), Person("cat", 21), Person("jim", 30))

/**
 * 获取第一个大于17岁的或最后一个大于17岁的姓名
 */
fun testFirstAndLast() {
    people.filter { it.age > 17 }.map { it.name }.first().run(::println)
    people.filter { it.age > 17 }.map { it.name }.last().run(::println)
}

/**
 * 获取嵌套列表中所有元素并处理
 *
 * 获取所有人姓名及小写的反写并放在List
 */
fun testFlatten() {
    people.map { it.name }.map { it.lowercase() }.map { name -> listOf(name, name.reversed()) }.flatten().run(::println)
    people.map { it.name }.map { it.lowercase() }.flatMap { name -> listOf(name, name.reversed()) }.run(::println)
}

/**
 * 排序
 */
fun testSort() {
    //asc
    people.filter { it.age > 10 }.sortedBy { it.age }
    //desc
    people.filter { it.age > 10 }.sortedByDescending { it.age }
}

/**
 * 按首字母分组
 */
fun testGroup() {
    people.groupBy { it.name.first() }.run(::println)
}

/**
 * O(n1)*O(n2)... 产生大量中间结果 性能不佳
 */
fun testEagerInnerIteration() {
    people.filter(::isAdult).map(::fetchName).first().run(::println)
}

/**
 * Sequence不支持并行
 */
/**
 * 使用序列 延迟计算 避免直接使用内部迭代产生大量中间结果
 * 仅需要O(n)
 */
fun testLazySequence() {
    people.asSequence().filter(::isAdult).map(::fetchName).first().run(::println)
}

fun isAdult(person: Person): Boolean {
    println("called isAdult for ${person.name}")
    return person.age > 17
}

fun fetchName(person: Person): String {
    println("fetch name for ${person.name}")
    return person.name
}

/**
 * 无限序列
 */
/**
 * 无限质数序列
 */
fun testInfiniteSequence() {
    val sequence = generateSequence(5, ::nextPrime)
    sequence.take(5).toList().run(::println)
}

fun isPrime(n: Long) = (n > 1) && (2 until n).none { n % it == 0L }

tailrec fun nextPrime(n: Long): Long = if (isPrime(n + 1)) n + 1 else nextPrime(n + 1)

/**
 * next函数与生成序列可放在一起
 */
fun testSequenceFunction() {
    val primes = sequence {
        var i = 0L
        while (true) {
            i++
            if (isPrime(i)) {
                yield(i)
            }
        }
    }
    primes.drop(2).take(5).toList().run(::println)
}

