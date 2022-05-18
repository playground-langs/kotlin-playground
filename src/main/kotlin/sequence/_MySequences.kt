package sequence

fun <T> Iterable<T>.asMySequence(): MySequence<T> {
    return mySequence { this.iterator() }
}

inline fun <T> mySequence(crossinline iterator: () -> Iterator<T>): MySequence<T> =
    object : MySequence<T> {
        override fun iterator(): Iterator<T> = iterator()
    }

fun <T> MySequence<T>.filter(predicate: (T) -> Boolean): MySequence<T> {
    return MyFilteringSequence(this, true, predicate)
}

fun <T, R> MySequence<T>.map(transformer: (T) -> R): MySequence<R> {
    return MyTransformingSequence(this, transformer)
}

fun <T> MySequence<T>.take(n: Int): MySequence<T> {
    return MyTakeSequence(this, n)
}

fun <T> MySequence<T>.drop(n: Int): MySequence<T> {
    return MyDropSequence(this, n)
}

fun <T> MySequence<T>.first(): T {
    val iterator = this.iterator()
    if (!iterator.hasNext()) {
        throw NoSuchElementException("sequence is empty")
    }
    return iterator.next()
}

fun <T> MySequence<T>.last(): T {
    val iterator = this.iterator()
    if (!iterator.hasNext()) {
        throw NoSuchElementException("sequence is empty")
    }
    var last = iterator.next()
    while (iterator.hasNext()) {
        last = iterator.next()
    }
    return last
}

fun <T> MySequence<T>.count(): Int {
    var count = 0
    for (element in this) {
        count++
    }
    return count
}

fun MySequence<Int>.sum(): Int {
    var sum = 0
    for (element in this) {
        sum += element
    }
    return sum
}

fun <T> MySequence<T>.forEach(action: (T) -> Unit): Unit {
    for (element in this) {
        action(element)
    }
}

fun <T> MySequence<T>.toList(): List<T> {
    val list = mutableListOf<T>()
    for (element in this) {
        list.add(element)
    }
    return list.toList()
}