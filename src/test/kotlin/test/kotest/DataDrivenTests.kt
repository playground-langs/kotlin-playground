package test.kotest

import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe


//data driven before v4.2
class MyTest1 : StringSpec({
    "maximum of two numbers" {
        forAll(
            row(1, 5, 5),
            row(1, 0, 1),
            row(0, 0, 0),
        ) { a, b, max ->
            a.coerceAtLeast(b) shouldBe max
        }
    }
})

class MyTest2 : FreeSpec({
    "Addition" - {
        listOf(
            row("1+0", 1) { 1 + 0 },
            row("1+1", 2) { 1 + 1 },
        ).map { (desc: String, expected: Int, math: () -> Int) ->
            desc {
                math() shouldBe expected
            }
        }
    }
})

fun isPythagTriple(a: Int, b: Int, c: Int) = a * a + b * b == c * c

data class PythagTriple(val a: Int, val b: Int, val c: Int)

/**
 * 新的数据驱动测试形式
 */
class MyTest3 : FunSpec({
    //with default name
    context("pythag triples tests 1") {
        withData(
            PythagTriple(3, 4, 5),
            PythagTriple(6, 8, 10),
            PythagTriple(8, 15, 17),
            PythagTriple(7, 24, 25)
        ) { (a, b, c) ->
            isPythagTriple(a, b, c) shouldBe true
        }
    }
    //使用map定义用例名称
    context("pythag triples tests 2") {
        withData(
            mapOf(
                "3,4,5" to PythagTriple(3, 4, 5),
                "6,8,10" to PythagTriple(6, 8, 10),
                "8,15,17" to PythagTriple(8, 15, 17),
                "7,24,25" to PythagTriple(7, 24, 25)
            )
        ) { (a, b, c) ->
            isPythagTriple(a, b, c) shouldBe true
        }
    }
    //使用nameFn
    context("pythag triples tests 3") {
        withData(
            nameFn = { "${it.a}^2+${it.b}^2=${it.c}^2" },
            PythagTriple(3, 4, 5),
            PythagTriple(6, 8, 10),
            PythagTriple(8, 15, 17),
            PythagTriple(7, 24, 25)
        ) { (a, b, c) ->
            isPythagTriple(a, b, c) shouldBe true
        }
    }
})