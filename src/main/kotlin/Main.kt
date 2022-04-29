fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    val greet = "func.hello"
    println(greet)
    println(greet::class)
    println(greet.javaClass)

    println("hi" == "hi")
    println("hi" === "hi")
    println(null == "hi")
    println(null == null)
    println(null === null)
    val multiline = """shell
    |java
           |cpp
    """.trimMargin()
    println(multiline)
    func.greet()
    func.change(Person("tom", 12))
}