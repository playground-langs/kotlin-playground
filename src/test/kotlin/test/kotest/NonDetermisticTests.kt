package test.kotest

import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.framework.concurrency.eventually
import io.kotest.framework.concurrency.fixed
import kotlinx.coroutines.delay

@OptIn(ExperimentalKotest::class)
class NonDetermisticTests : FunSpec({
    test("eventually") {
        eventually(5000) {
            delay(600)
        }
    }
    test("interval retry exception") {
        eventually({
            duration = 5000
            interval = 1000L.fixed()
            retries = 10
            suppressExceptions = setOf(RuntimeException::class)
        }) {
            delay(600)
        }
    }

    test("predicate") {
        var str = ""
        eventually<String>({
            duration = 5000
            predicate = { it.result == "xxx" }
        }) {
            str += "x"
            str
        }
    }
})

