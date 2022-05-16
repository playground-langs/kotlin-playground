package fluency.any

/**
 * let also apply run
 */

class Context {
    private val format = "%-10s%-10s%-10s%-10s"

    private val str = "context"

    private val result = "RESULT"

    override fun toString() = "lexical"

    fun formatAnyMethods() {
        println(
            String.format(
                "%-10s%-10s%-10s%-10s%-10s",
                "Method", "Argument", "Receiver", "Return", "Result"
            )
        )
        val result1 = str.let { arg ->
            print(String.format(format, "let", arg, this, result))
            result
        }
        println(String.format("%-10s", result1))
        val result2 = str.also { arg ->
            print(String.format(format, "also", arg, this, result))
            result
        }
        println(String.format("%-10s", result2))
        val result3 = str.run {
            print(String.format(format, "run", "N/A", this, result))
            result
        }
        println(String.format("%-10s", result3))
        val result4 = str.apply {
            print(String.format(format, "apply", "N/A", this, result))
            result
        }
        println(String.format("%-10s", result4))
    }
}

class Mailer {
    val detail = StringBuilder()
    fun from(addr: String) = detail.append("from $addr ...\n")
    fun to(addr: String) = detail.append("to $addr ...\n")
    fun subject(line: String) = detail.append("subject $line ...\n")
    fun body(message: String) = detail.append("body $message ...\n")
    fun send() = detail.append("...sending ...\n $detail")
}

val from = "builder@agiledeveloper.com"
val to = "venkats@agiledeveloper.com"
val subject = "your code sucks"
val body = "...details..."

/**
 * 示例代码:混乱而重复
 */
fun testUseNothing() {
    val mailer = Mailer()
    mailer.from(from)
    mailer.to(to)
    mailer.subject(subject)
    mailer.body(body)
    val result = mailer.send()
    println(result)
}

/**
 * apply:减少调用对象的重复
 * 没有必要使用多个apply
 */
fun testUseApply1() {
    val mailer = Mailer().apply { from(from) }
        .apply { to(to) }
        .apply { subject(subject) }
        .apply { body(body) }
    val result = mailer.send()
    println(result)
}

fun testUseApply2() {
    val mailer = Mailer().apply {
        from(from)
        to(to)
        subject(subject)
        body(body)
    }
    val result = mailer.send()
    println(result)
}

/**
 *run 获取结果
 */
fun testUseRun() {
    val result = Mailer().run {
        from(from)
        to(to)
        subject(subject)
        body(body)
        send()
    }
    println(result)
}

/**
 * let将对象作为参数传递 同时保留上下文this
 */
fun createMailer() = Mailer()
fun prepareAndSend(mailer: Mailer) = mailer.run {
    from(from)
    to(to)
    subject(subject)
    body(body)
    send()
}

fun testNoUseLet() {
    val mailer = createMailer()
    prepareAndSend(mailer)
}

fun testUseLet1() {
    val result = createMailer().let { mailer ->
        prepareAndSend(mailer)
    }
    println(result)
}

fun testUseLet2() {
    val result = createMailer().let(::prepareAndSend)
    println(result)
}

/**
 * also：将void方法连接起来
 */
fun prepareMailer(mailer: Mailer): Unit {
    mailer.run {
        from(from)
        to(to)
        subject(subject)
        body(body)
    }
}

fun sendMail(mailer: Mailer): Unit {
    mailer.send()
}

fun testNoUseAlso() {
    val mailer = createMailer()
    prepareMailer(mailer)
    sendMail(mailer)
}

fun testUseAlso() {
    createMailer().also(::prepareMailer).also(::sendMail)
}

/**
 * takeUnless takeIf with repeat run
 */