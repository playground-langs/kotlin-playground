package coroutine.ext.promise

import coroutine.log

fun main() {
    async {
        log("await start")
        val value = await {
            Thread.sleep(1500)
            10
        }
        log("await end")
        println(value)
    }
    //此api不能支持结构化并发
    Thread.sleep(3000)
    log("main end")
}