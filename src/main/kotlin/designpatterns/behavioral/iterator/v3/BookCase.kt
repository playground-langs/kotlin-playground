package designpatterns.behavioral.iterator.v3

/**
 * 处理引入的第三方类
 */
data class Book(private val name: String)
class BookCase(val books: List<Book>) {
}

//使用扩展函数
operator fun BookCase.iterator() = this.books.iterator()

//或者使用匿名对象自定义迭代行为
fun BookCase.listIterator() = object : Iterator<Book> {
    val iterator = books.iterator()
    override fun hasNext() = iterator.hasNext()

    override fun next() = iterator.next()

}

fun main() {
    val book1 = Book("book1")
    val book2 = Book("book2")
    val book3 = Book("book3")
    val bookcase = BookCase(mutableListOf(book1, book2, book3))
    for (book in bookcase) {
        println(book)
    }
}