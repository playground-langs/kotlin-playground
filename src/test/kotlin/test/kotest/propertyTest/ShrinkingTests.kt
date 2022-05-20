package test.kotest.propertyTest

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.Shrinker
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.nonNegativeInt
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.checkAll

/**
 * FreeSpec 支持测试嵌套
 */
class ShrinkingTests : FreeSpec({
    //失败用例会自动缩小范围找到最接近的用例
    //! bang符号忽略测试 f: 仅运行标记测试忽略其他测试
    "!test default shrinking" - {
        Arb.positiveInt(20000).checkAll { i ->
            calculateProperty(i) shouldBe true
        }
    }
    //自定义generator没有定义shrinker，需要自己定义
    "!custom shrinker" - {
        "coordinate transformations" - {
            //搜索四个邻近的坐标点
            val coordinateShrinker = Shrinker<Coordinate> { c ->
                listOf(Coordinate(c.x - 1, c.y))
                listOf(Coordinate(c.x, c.y - 1))
                listOf(Coordinate(c.x + 1, c.y))
                listOf(Coordinate(c.x, c.y + 1))
            }
            val coordinateArb = arbitrary(coordinateShrinker) {
                Coordinate(Arb.nonNegativeInt().bind(), Arb.nonNegativeInt().bind())
            }
            "coordinates are always be positive after transformation" {
                coordinateArb.checkAll {
                    transform(it).x shouldBeGreaterThan 0
                    transform(it).y shouldBeGreaterThan 0
                }
            }
        }
    }
})

fun calculateProperty(i: Int) = i <= 18000

data class Coordinate(val x: Int, val y: Int)

fun transform(coordinate: Coordinate) = Coordinate(coordinate.x - 1, coordinate.y - 1)