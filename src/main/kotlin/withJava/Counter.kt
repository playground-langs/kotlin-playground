package withJava

data class Counter(val value: Int) {
    operator fun plus(other: Counter) = Counter(value + other.value)

    fun map(mapper: (Counter) -> Counter): Counter = mapper(this)

    /**
     * 生成重载方法
     */
    @JvmOverloads
    fun add(n: Int = 1) = Counter(value + n)

    companion object {
        /**
         * 添加该注解可使java以static形式调用
         */
        @JvmStatic
        fun create(n: Int) = Counter(n)
    }
}