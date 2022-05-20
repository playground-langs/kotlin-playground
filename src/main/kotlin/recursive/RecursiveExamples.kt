package recursive

import java.math.BigInteger


/**
 * 普通阶乘
 */
fun factorial(n: Int): BigInteger = if (n <= 0) 1.toBigInteger() else n.toBigInteger() * factorial(n - 1)

/**
 * 尾递归优化
 * 尾递归:递归调用是函数的最后一个操作
 * 尾递归优化的思路:需要将计算所需依赖变量(本例的依赖为上一次计算结果)当做参数在递归调用时传递并在满足中止条件时返回
 * 尾递归形式可以优化
 * 1.理论基础:后一次递归计算不需要前一次递归的栈数据(仅通过函数参数传递)因而可以复用前一次调用栈从而避免堆栈溢出,一定可以通过编译器转化为迭代形式
 * 2.实现方式:由编译器将尾递归代码编译为迭代实现
 */
/**
public static final BigInteger factorialTailRec(int n, @NotNull BigInteger acc) {
Intrinsics.checkNotNullParameter(acc, "acc");
int i = n;
BigInteger bigInteger = acc;
while (true) {
BigInteger bigInteger2 = bigInteger;
int i2 = i;
if (i2 <= 0) {
return bigInteger2;
}
i = i2 - 1;
BigInteger valueOf = BigInteger.valueOf(i2);
Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this.toLong())");
BigInteger multiply = valueOf.multiply(bigInteger2);
Intrinsics.checkNotNullExpressionValue(multiply, "this.multiply(other)");
bigInteger = multiply;
}
}
 */
tailrec fun factorialTailRec(n: Int, acc: BigInteger = BigInteger("1")): BigInteger =
    if (n <= 0) acc else factorialTailRec(n - 1, n.toBigInteger() * acc)

/**
 * 函数式迭代实现
 */
fun factorialFun(n: Int) = (1..n).fold(BigInteger("1")) { acc, e ->
    e.toBigInteger() * acc
}

/**
 * 迭代实现:与函数式实现的展开形式等价
 */
fun factorialIteration(n: Int): BigInteger {
    var acc = BigInteger("1")
    for (e in (1..n)) {
        acc *= e.toBigInteger()
    }
    return acc
}

fun main() {
    factorial(5).run(::println)
    factorialFun(5).run(::println)
    factorialIteration(5).run(::println)
    testFactorialTailRec()
}

/**
 * 无尾递归优化的版本会导致堆栈溢出
 */
fun testOverflow() {
    val n = 50000
    factorial(n).run(::println)
    factorialIteration(n).run(::println)
}

fun testFactorialTailRec() {
    val n = 50000
    println("尾递归计算:")
    factorialTailRec(n).run(::println)
    println("迭代计算:")
    factorialFun(n).run(::println)
}