package async.future

import java.util.concurrent.CompletableFuture

fun jsonCompletableFuture(url: String): CompletableFuture<String> =
    CompletableFuture.supplyAsync {
        //io
        "ok $url"
    }

fun main() {
    val urls = listOf<String>(
        "www.test1.com/json",
        "www.test2.com/json",
        "www.test3.com/json",
    )
    urls.map {
        jsonCompletableFuture(it)
    }.let { futureList ->
        CompletableFuture.allOf(*futureList.toTypedArray())
            .thenApply {
                futureList.map {
                    it.get()
                }
            }.thenAccept {
                println(it)
            }
    }
    Thread.sleep(2000)
}