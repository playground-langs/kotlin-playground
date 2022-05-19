package com.techzealot.kt;

import withJava.Counter;
import withJava.MyUtils;

import java.io.FileNotFoundException;

public class CallKotlin {

    /**
     * 调用static
     */
    public static void testStatic() {
        System.out.println(Counter.create(10));
    }

    /**
     * 传递lambda,可以直接使用lambda表达式访问kotlin lambda
     */
    public static void testLambda() {
        Counter c = Counter.create(10).map(counter -> counter.plus(new Counter(10)));
        System.out.println(c);
    }

    public static void main(String[] args) {
        testLambda();
    }

    /**
     * 测试throws
     * 使用自定义类名访问顶级函数
     */
    public static void testThrows() {
        try {
            MyUtils.readFile("not exist");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * kotlin中操作符可以使用对应名称访问
     */
    public void testOperator() {
        System.out.println(new Counter(1).plus(new Counter(2)));
    }

    /**
     * 调用带默认参数的方法
     */
    public void testDefaultParam() {
        new Counter(10).add();
        new Counter(10).add(10);
    }
}
