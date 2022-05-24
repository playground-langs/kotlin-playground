package designpatterns.behavioral.iterator.v1

/**
 * 使用kotlin Iterable进行操作符重载以便使用for..in
 */
data class Book(private val name: String)
class BookCase(private val books: List<Book>) : Iterable<Book> {
    override operator fun iterator(): Iterator<Book> = books.iterator()
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