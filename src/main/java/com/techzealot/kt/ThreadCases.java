package com.techzealot.kt;

public class ThreadCases {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //抛出InterruptedException后会清除中断状态 因此如果捕获异常要重新设置中断状态
                        Thread.sleep(1000);
                        //中断异常两种处理方法:
                        //1.重新抛出
                        //2.如果不能抛出异常则需要重设中断状态否则中断信号可能会丢失导致无法中断
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    if (Thread.interrupted()) {
                        return;
                    }
                }
            }
        });
        t.start();
        t.interrupt();
    }
}
