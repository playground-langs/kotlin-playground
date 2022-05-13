package fp

/**
 * 默认情况下lambda不允许有return，会产生混乱，不明确return到哪里
 * 匿名函数在非单行表达式时必须加return
 */
fun defaultCase() {
    var lambda: (Int) -> Int = { e -> e * 2 }
    //error
    //lambda = { e -> return e * 2 }
    lambda = fun(e: Int): Int { return e * 2 }
    lambda = fun(e: Int) = e * 2
}

/**
 * lambda表达式可以通过标签返回到lambda调用处
 * 接收lambda的函数为隐式标签
 * 建议使用自定义标签
 * 标签return导致控制流跳转到标签块的末尾
 */
fun labelCase() {
    (1..3).forEach { i ->
        invokeWith(i) here@{
            println("enter for $it")
            if (it == 2) {
                return@here
            }
            println("exit for $it")
        }
    }
    println("end for call")
}

fun noLabelCase() {
    (1..3).forEach { i ->
        invokeWith(i) {
            println("enter for $it")
            if (it == 2) {
                //error 不允许直接return
                //return
            }
            println("exit for $it")
        }
    }
    println("end for call")
}

fun invokeWith(n: Int, action: (Int) -> Unit) {
    println("enter invokeWith $n")
    action(n)
    println("exit invokeWith $n")
}

/**
 * inline 非局部return
 */

/**
 * forEach为inline函数 会导致return到调用者
 */
fun inlineReturnCase1(): Int {
    (1..3).forEach {
        if (it == 2) {
            return -1
        }
        println("iterate $it")
    }
    return 1
}

/**
 * Kotlin内联函数的作用是消除lambda带来的额外开销
 * 普通函数最好不要inline,jvm会根据情况自动优化
 * 普通函数inline后return也是返回自身，不能返回调用者，内联时会删除return 保持了代码的清晰以及return的语义
 * return 即返回return代码位置定义的函数 与普通函数return具有直觉一致性
 */
inline fun returnOuter(i: Int): Int {
    println("$i")
    return i + 1
}

/**
 * inline return 无效
 */
fun inlineCase2(): Int {
    val i = returnOuter(1)
    println(i)
    return -1
}

/**
 * #########高阶函数inline return
 * inline后既可以非局部返回也可以返回到标签
 */

/**
 * 高阶函数不inline的开销:
 * 1.每个lambda多创建了一个Function对象并且多了两次invoke的函数调用(一次lambda的invoke一次编译器生成的兼容泛型的桥接方法)
 * 该函数返回值lambda的java代码如下:
final class LambdaReturnCasesKt$invokeTwo$1 extends Lambda implements Function1<Integer, Unit> {
final /* synthetic */ int $n;

/* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
LambdaReturnCasesKt$invokeTwo$1(int $n) {
super(1);
this.$n = $n;
}

public final void invoke(int i) {
System.out.println(i * this.$n);
}

public /* bridge */ /* synthetic */ Object invoke(Object p1) {
invoke(((Number) p1).intValue());
return Unit.INSTANCE;
}
}
 *
 * 2.纯函数lambda只有一个静态Function实例，闭包每次都会创建一个Function对象
 */
fun invokeTwo(n: Int, action1: (Int) -> Unit, action2: (Int) -> Unit): (Int) -> Unit {
    println("enter invokeTwo $n")
    action1(n)
    action2(n)
    println("exit invokeTwo $n")
    return { i -> println(i * n) }
}

/**
 * 两个参数lambda均为纯函数对应java代码为:静态实例存储Function
final class LambdaReturnCasesKt$testInvokeTwo$1 extends Lambda implements Function1<Integer, Unit> {
public static final LambdaReturnCasesKt$testInvokeTwo$1 INSTANCE = new LambdaReturnCasesKt$testInvokeTwo$1();

LambdaReturnCasesKt$testInvokeTwo$1() {
super(1);
}

public final void invoke(int i) {
LambdaReturnCasesKt.report(i);
}
//子类继承父类指定具体泛型时由编译器自动生成
public /* bridge */ /* synthetic */ Object invoke(Object p1) {
invoke(((Number) p1).intValue());
return Unit.INSTANCE;
}
}
 */
fun testInvokeTwo() {
    invokeTwo(1, { i -> report(i) }, { i -> report(i) })
}

/**
 *
fp.LambdaReturnCasesKt # printStackTrace(()void)
fp.LambdaReturnCasesKt # report((int)void)
fp.LambdaReturnCasesKt$testInvokeTwo$2 # invoke((int)void)
fp.LambdaReturnCasesKt$testInvokeTwo$2 # invoke((Object)Object)
fp.LambdaReturnCasesKt # invokeTwo((int,Function1,Function1)Function1)
fp.LambdaReturnCasesKt # testInvokeTwo(()void)
fp.LambdaReturnCasesKt # main(()void)
fp.LambdaReturnCasesKt # main((String[])void)
 */
fun report(n: Int) {
    println()
    println("called with $n")
    printStackTrace()
}

/**
 * 打印调用栈可获取类名 方法名 参数类型
 */
fun printStackTrace() {
    val walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
    walker.forEach { frame: StackWalker.StackFrame ->
        println(
            "${frame.className} # ${frame.methodName}(${frame.methodType})"
        )
    }
}

/**
 * 高阶函数inline
 * inline不仅会inline当前函数 而且会inline函数参数中所有的lambda但不会inline返回值中的lambda
inline后生成的java代码:
public static final void testInvokeTwoInline() {
System.out.println((Object) ("enter invokeTwo " + 1));
report(1);
report(1);
System.out.println((Object) ("exit invokeTwo " + 1));
new invokeTwoInline.1(1).invoke(1);
}
 */
inline fun invokeTwoInline(n: Int, action1: (Int) -> Unit, action2: (Int) -> Unit): (Int) -> Unit {
    println("enter invokeTwo $n")
    action1(n)
    action2(n)
    println("exit invokeTwo $n")
    return { i -> println(i * n) }
}

fun testInvokeTwoInline() {
    invokeTwoInline(1, { i -> report(i) }, { i -> report(i) })(1)
}

/**
 * noinline可以选则指定lambda参数不inline
 */
inline fun invokeTwoPartialInline(n: Int, noinline action1: (Int) -> Unit, action2: (Int) -> Unit): (Int) -> Unit {
    println("enter invokeTwo $n")
    action1(n)
    action2(n)
    println("exit invokeTwo $n")
    return { i -> println(i * n) }
}

/**
 *
public static final void testInvokeTwoPartialInline() {
Function1 action1$iv = testInvokeTwoPartialInline.1.INSTANCE;
System.out.println((Object) ("enter invokeTwo " + 1));
action1$iv.invoke(1);
report(1);
System.out.println((Object) ("exit invokeTwo " + 1));
new invokeTwoPartialInline.1(1).invoke(1);
}
 */
fun testInvokeTwoPartialInline() {
    invokeTwoPartialInline(1, { i -> report(i) }, { i -> report(i) })(1)
}

/**
 * crossinline可以让lambda在传递时也inline
 * crossinline不能非局部return
 */
inline fun invokeTwoCrossInline(n: Int, action1: (Int) -> Unit, crossinline action2: (Int) -> Unit): (Int) -> Unit {
    println("enter invokeTwo $n")
    action1(n)
    action2(n)
    println("exit invokeTwo $n")
    //TODO 测试crossinline的内联传递效果
    //exchangeInline(n, action2)
    //传递lambda
    return { i -> action2(i * n) }
}

/**
public static final int testInvokeTwoCrossInline() {
System.out.println((Object) ("enter invokeTwo " + 1));
report(1);
if (1 > 1) {
return 1;
}
report(1);
System.out.println((Object) ("exit invokeTwo " + 1));
Function1 func = new LambdaReturnCasesKt$testInvokeTwoCrossInline$.inlined.invokeTwoCrossInline.1(1);
func.invoke(1);
return 0;
}
 */
fun testInvokeTwoCrossInline(): Int {
    val func = invokeTwoCrossInline(1, { i ->
        report(i)
        //此处可以非局部return
        // 直接返回testInvokeTwoCrossInline
        // 此处不能返回lambda要求的Unit,应返回调用函数要求的类型
        if (i > 1) return 1
    }, { i ->
        report(i)
        //error 标记有crossinline和noinline的lambda无法非局部return
        //return
    })
    //此处不会inline
    func(1)
    return 0
}

fun exchangeInline(n: Int, action: (Int) -> Unit) {
    println("exchange")
    action(n)
}





