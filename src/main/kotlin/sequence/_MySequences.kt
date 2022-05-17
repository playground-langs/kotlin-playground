package sequence

fun <T> Iterable<T>.asMySequence(): MySequence<T> {
    return mySequence { this.iterator() }
}

inline fun <T> mySequence(crossinline iterator: () -> Iterator<T>): MySequence<T> =
    object : MySequence<T> {
        override fun iterator(): Iterator<T> = iterator()
    }