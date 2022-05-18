package sequence

/**
 * 实现延迟的filter->map->take/drop->first/last/forEach/count/sum/toList
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

/**
 * 未优化take与drop连用情形,类似窥孔优化，如:
 * 1.链路上有take(0)则后续无需执行 需要一个特殊的EmptySequence处理
 * 2.take(n1).take(n2),n2>=n1时等价于take(n1)
 * 3.take(n1).drop(n2),n2>=n1时产生空序列;n2<n1时等价于take(start,end)
 * 4.drop(n1).drop(n2)==drop(n1+n2)
 * ...
 * 如有多个take/drop组合可优化掉大量中间sequence对象，从而省去大量相关计算，优化性能
 */
class MyTakeSequence<T>(val sequence: MySequence<T>, val n: Int) : MySequence<T> {
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        val iterator = sequence.iterator()
        var count = n
        override fun hasNext(): Boolean {
            return iterator.hasNext() && count > 0
        }

        override fun next(): T {
            count--
            return iterator.next()
        }
    }
}

class MyDropSequence<T>(val sequence: MySequence<T>, val n: Int) : MySequence<T> {
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        val iterator = sequence.iterator()
        var count = n

        private fun drop() {
            while (count > 0 && iterator.hasNext()) {
                iterator.next()
                count--
            }
        }

        override fun hasNext(): Boolean {
            drop()
            return iterator.hasNext()
        }

        override fun next(): T {
            drop()
            return iterator.next()
        }
    }
}


