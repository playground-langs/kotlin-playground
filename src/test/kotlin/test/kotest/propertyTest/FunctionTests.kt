package test.kotest.propertyTest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import io.kotest.property.forAll

class PropertyExample : StringSpec({
    "String size use forall" {
        forAll<String, String> { a, b ->
            //true expression
            (a + b).length == a.length + b.length
        }
    }

    "String size use checkAll" {
        checkAll<String, String> { a, b ->
            //assertions
            (a + b).length shouldBe a.length + b.length
        }
    }

    "a many iteration test" {
        //默认迭代1000次
        checkAll<Double, Double>(10000) { a, b ->
            a + b shouldBe b + a
        }
    }
    "specify generators" {
        //指定随机生成器范围
        forAll(Arb.int(10, 1000)) { a ->
            a < 2000
        }
    }
})