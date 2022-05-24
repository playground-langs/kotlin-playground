package designpatterns.behavioral.iterator.v2

/**
 * 使用操作符重载以便使用for..in
 * 可以不用显式实现Iterable接口
 */
data class Book(private val name: String)
class BookCase(private val books: List<Book>) {
    operator fun iterator(): Iterator<Book> = books.iterator()
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