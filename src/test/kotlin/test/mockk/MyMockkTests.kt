package test.mockk

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class MyMockkTests : BehaviorSpec({
    given("prepare mother and kid") {
        //relaxed 允许mock对象的方法不指定行为
        val mother = mockk<Mother>(relaxed = true)
        val kid = Kid(mother)
        val slot = slot<Int>()
        every { mother.giveMoney() } returns 30
        //捕获参数
        every { mother.inform(capture(slot)) } just runs
        When("want money") {
            kid.wantMoney()
        }
        then("kid.money==30") {
            kid.money shouldBe 30
            slot.captured shouldBe 100
            //验证方法调用次数
            verify(exactly = 1) {
                mother.inform(any())
            }
            //验证调用顺序
            verifyOrder {
                mother.inform(any())
                mother.giveMoney()
            }
        }
    }
})

class Kid(val mother: Mother) {
    var money = 0
        private set

    fun wantMoney() {
        mother.inform(100)
        money += mother.giveMoney()
    }
}

class Mother() {
    fun giveMoney(): Int = 100
    fun inform(money: Int) {
        println("I want $money")
    }
}