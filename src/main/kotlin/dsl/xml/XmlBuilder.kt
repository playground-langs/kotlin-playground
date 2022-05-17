package dsl.xml

/**
 * 实现dsl的步骤：
 * 1.先写出dsl形式的代码
 * 2.根据dsl形式从外到内逐步实现
 * 3.可根据dsl形式判断出返回值和参数
 */
@XmlMarker
class XmlBuilder {
    fun root(rootName: String, block: Node.() -> Unit): Node =
        Node(rootName).apply(block)
}

@XmlMarker
class Node(val name: String) {
    private val attributes = mutableMapOf<String, String>()
    private val children = mutableListOf<Node>()
    var textValue = ""
    fun text(value: String) {
        textValue = value
    }

    fun element(
        childName: String, vararg attributeValues: Pair<String, String>,
        block: Node.() -> Unit
    ) {
        val child = Node(childName)
        attributeValues.forEach { child.attributes += it }
        children += child
        child.run(block)
    }

    override fun toString(): String {
        return toString(0)
    }

    private fun toString(indentation: Int): String {
        val indent = " ".repeat(indentation)
        val depth = 2
        val attributeValues =
            if (attributes.isEmpty()) "" else attributes.map { "${it.key}='${it.value}'" }.joinToString(" ", " ")
        return if (textValue.isNotEmpty()) "$indent<$name$attributeValues>$textValue</$name>"
        else
            """$indent<$name$attributeValues>
                |${children.joinToString("\n") { it.toString(indentation + depth) }}
                |$indent</$name>
            """.trimMargin()
    }
}

fun xml(block: XmlBuilder.() -> Node): Node = XmlBuilder().block()

val langsAndAuthors = mapOf("Javascript" to "Eich", "Java" to "Gosling", "Ruby" to "Matz")

fun testXmlDsl() {
    val xml = xml {
        root("languages") {
            langsAndAuthors.forEach { (name, author) ->
                element("language", "name" to name) {
                    element("author") {
                        text(author)
                    }
                }
            }
        }
    }
    println(xml.toString())
}

@DslMarker
annotation class XmlMarker

/**
 * 可以使用DslMarker注解限制嵌套lambda隐式调用父接收方的方法
 * 标注此注解只允许调用直接接收方的方法
 */
fun testDslOutOfScope() {
    xml {
        root("root") {
            element("test") {
                //error
                //root("root1") {}
                //但仍可以显式调用,但不推荐
                this@xml.root("root1") {}
            }
        }
    }
}