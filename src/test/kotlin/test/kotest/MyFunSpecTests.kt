package test.kotest

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveLength

class MyFunSpecTests : FunSpec({
    test("my first fun test") {
        1 + 2 shouldBe 3
    }
})

class DynamicTests : FunSpec({
    //动态生成测试用例
    listOf(
        "sam",
        "pam",
        "tim",
    ).forEach {
        test("$it should be three letter") {
            it shouldHaveLength 3
        }
    }
})

class Callbacks : FunSpec({
    beforeEach {
        println("Hello from $it")
    }

    test("sam should be three letter") {
        "sam" shouldHaveLength 3
    }

    afterEach {
        println("goodbye from $it")
    }
})