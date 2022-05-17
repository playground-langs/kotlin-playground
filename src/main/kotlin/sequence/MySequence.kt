package sequence

/**
 * 实现延迟的filter->map->first/last/forEach/count/take/toList
 */
interface MySequence<out T> {
    operator fun iterator(): Iterator<T>
}

class MyTransformingSequence<T, R>(val sequence: MySequence<T>, val transformer: (T) -> R) : MySequence<R> {

    override fun iterator(): Iterator<R> = object : Iterator<R> {
        val iterator = sequence.iterator()
        override fun hasNext(): Boolean {
            return iterator.hasNext()
        }

        override fun next(): R {
            return transformer(iterator.next())
        }
    }
}

class MyFilteringSequence<T>(
    val sequence: MySequence<T>, val sendWhen: Boolean = true, val predicate: (T) -> Boolean
) : MySequence<T> {
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        val iterator = sequence.iterator()
        var nextItem: T? = null

        //-1:未开始一轮计算或计算完所有仍未发现满足要求的一个
        // 1:有下一个
        // 0:非法状态,调用次数超过元素个数
        var nextState = -1
        fun calcNext() {
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (predicate(next) == sendWhen) {
                    nextItem = next
                    nextState = 1
                    return
                }
            }
            nextState = 0
        }

        override fun hasNext(): Boolean {
            if (nextState == -1)
                calcNext()
            return nextState == 1
        }

        override fun next(): T {
            if (nextState == -1)
                calcNext()
            if (nextState == 0)
                throw NoSuchElementException()
            val result = nextItem
            //清除上一轮转态数据
            nextState = -1
            nextItem = null
            return result as T
        }

    }
}




