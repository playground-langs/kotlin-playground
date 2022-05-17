package dsl

import dsl.DateUtil.Tense
import dsl.DateUtil.Tense.ago
import dsl.DateUtil.Tense.from_now
import java.time.LocalDate


class DateUtil(val number: Int, val tense: Tense) {
    enum class Tense {
        ago, from_now
    }

    override fun toString(): String {
        val today = LocalDate.now()
        return when (tense) {
            ago -> today.minusDays(number.toLong()).toString()
            from_now -> today.plusDays(number.toLong()).toString()
        }
    }
}

infix fun Int.days(timing: Tense) = DateUtil(this, timing)

fun testDateDsl() {
    println(2 days ago)
    println(2 days from_now)
}