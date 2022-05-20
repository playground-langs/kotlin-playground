package test.kotest.propertyTest

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.Exhaustive
import io.kotest.property.RandomSource
import io.kotest.property.arbitrary.*
import io.kotest.property.exhaustive.enum
import io.kotest.property.exhaustive.exhaustive
import io.kotest.property.exhaustive.ints
import io.kotest.property.forAll
import java.time.LocalDateTime

enum class Season { Winter, Fall, Spring, Summer }

data class Person(val name: String, val age: Int)

class GeneratorTests : StringSpec({
    //根据类型自动选择合适的generator
    "use forAll" {
        forAll<Int, Double, Boolean, String, LocalDateTime, Season>(10) { a, b, c, d, e, f ->
            println("$a $b $c $d $e $f")
            true
        }
    }
    //使用底层生成器Arbitrary 无限随机生成器
    "use generator" {
        Arb.int(1, 200).take(10).toList().run(::println)
        Arb.intArray(Arb.int(2, 8), Arb.int(200..400)).take(10).map { it.toList() }.toList().run(::println)
        Arb.char('a'..'z').take(10).toList().run(::println)
        Arb.stringPattern("\\w+[0-9]").take(10).toList().run(::println)
        Arb.list(Arb.int(), 1..5).take(10).toList().run(::println)
    }
    //使用有限生成器 Exhaustive
    "use exhaustive" {
        Exhaustive.enum<Season>().values.run(::println)
        Exhaustive.ints(1..10).values.run(::println)
    }
    //使用组合生成器
    "use complex operation" {
        Arb.choice(Arb.int(1..10), Arb.double(20.0..50.0)).take(10).toList().run(::println)
        Arb.bind(Arb.string(), Arb.int()) { name, age ->
            Person(name, age).run(::println)
        }
        Arb.choose(1 to Arb.int(1, 10), 2 to Arb.double(1.0, 5.0)).take(10).toList().run(::println)
        Arb.shuffle(listOf(1, 2, 3, 4, 5, 6)).take(10).toList().run(::println)
        Arb.subsequence(listOf(1, 2, 3, 4, 5, 6)).take(10).toList().run(::println)
    }
    //自定义生成器
    "custom generator" {
        val arb = arbitrary { rs: RandomSource ->
            rs.random.nextInt(1, 20)
        }
        arb.take(10).toList().run(::println)
        val personArb = arbitrary {
            val name = Arb.string(10..12).bind()
            val age = Arb.int(21..150).bind()
            Person(name, age)
        }
        personArb.take(10).toList().run(::println)
        val singleDigitPrimes = listOf(2, 3, 5, 7).exhaustive()
        singleDigitPrimes.values.run(::println)
    }
})
