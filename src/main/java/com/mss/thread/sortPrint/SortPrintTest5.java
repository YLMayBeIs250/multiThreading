package com.mss.thread.sortPrint;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

public class SortPrintTest5 {
    public static final Object object1 = new Object();

    public static final Object object2 = new Object();

    public static final Object object3 = new Object();

    public static void main(String[] args) {
        // 知识点1：使用多个Object对象锁 + wait、notify实现顺序打印十次ABC
        // 解释：
        // 线程1先执行，理所当然先获得object1和object2的锁，因此线程2被阻塞；线程3获得object3的锁，但是object3.wait()又释放了object3的锁，也被阻塞；
        // 线程1执行完synchronized(object2)代码块里的内容后释放了object2的锁，并调用object1.wait()释放object1的锁并阻塞；
        // 线程2被唤醒，获得object2和object3的锁，线程2执行完synchronized(object3)的内容后释放object3的锁，并调用object2.wait()释放object2的锁并阻塞；
        // 线程3被唤醒，获得object3和object1的锁，线程3执行完synchronized(object1)的内容后释放object1的锁，进入下一轮循环object3.wait()释放object3的锁并阻塞；
        // 循环打印，i是控制打印次数，让程序可以安全退出而不是遗留线程一直阻塞。
        System.out.println(PrintHelper.printThreadMark() + "[使用多个Object对象锁 + wait、notify实现顺序打印十次ABC]");
        new Thread(() -> {
            synchronized (object1) {
                for (int i = 1; i < 11; i++) {
                    synchronized (object2) {
                        SleepHelper.sleep(1000);
                        System.out.println(PrintHelper.printThreadMark() + "==>" + i);
                        System.out.println(PrintHelper.printThreadMark() + "A");
                        object2.notify();
                    }
                    try {
                        if (i < 10)
                            object1.wait();
                        else
                            break;
                    } catch (InterruptedException e) {
                        PrintHelper.printExceptionMark(e);
                    }
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (object2) {
                for (int i = 1; i < 11; i++) {
                    synchronized (object3) {
                        System.out.println(PrintHelper.printThreadMark() + "B");
                        object3.notify();
                    }
                    try {
                        if (i < 10)
                            object2.wait();
                        else
                            break;
                    } catch (InterruptedException e) {
                        PrintHelper.printExceptionMark(e);
                    }
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 1; i < 11; i++) {
                synchronized (object3) {
                    try {
                        object3.wait();
                    } catch (InterruptedException e) {
                        PrintHelper.printExceptionMark(e);
                    }
                    synchronized (object1) {
                        System.out.println(PrintHelper.printThreadMark() + "C");
                        object1.notify();
                    }
                }
            }
        }).start();
    }
}
