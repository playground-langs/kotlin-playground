package test.kotest

import io.kotest.core.spec.style.DescribeSpec

class MyDescribeTests : DescribeSpec({
    describe("score") {
        it("start as zero") {
            //test here
        }
        describe("with a strike") {
            it("adds ten") {

            }
        }
    }
})