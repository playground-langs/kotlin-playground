package async.future

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

val singleExecutor: ExecutorService by lazy {
    Executors.newSingleThreadExecutor()
}

fun jsonFuture(url: String): Future<String> {
    return singleExecutor.submit(Callable {
        //一些耗时IO操作...
        "ok $url"
    })
}

fun main() {
    val urls = listOf<String>(
        "www.test1.com/json",
        "www.test2.com/json",
        "www.test3.com/json",
    )
    urls.map {
        jsonFuture(it)
    }.map {
        //可能阻塞后续调用 效率不高
        //异常不好处理
        it.get()
    }.toList()
        .run(::println)
    singleExecutor.shutdownNow()
}