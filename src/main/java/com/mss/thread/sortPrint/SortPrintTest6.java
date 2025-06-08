package com.mss.thread.sortPrint;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.locks.LockSupport;

public class SortPrintTest6 {
    public static Thread thread1;

    public static Thread thread2;

    public static Thread thread3;

    public static void main(String[] args) {
        // 知识点1：LockSupport.unpark(thread2)可以指定唤醒哪个线程，比较灵活
        // LockSupport.park()阻塞当前线程，可以被其他线程的unpark()方法唤醒
        thread1 = new Thread(() -> {
            int count = 1;
            while (count < 11) {
                System.out.println(PrintHelper.printThreadMark() + "==>" + count++);
                System.out.println(PrintHelper.printThreadMark() + "A");
                LockSupport.unpark(thread2);
                LockSupport.park();
                SleepHelper.sleep(1000);
            }
        });

        thread2 = new Thread(() -> {
            int count = 1;
            while (count++ < 11) {
                LockSupport.park();
                System.out.println(PrintHelper.printThreadMark() + "B");
                LockSupport.unpark(thread3);
            }
        });

        thread3 = new Thread(() -> {
            int count = 1;
            while (count++ < 11) {
                LockSupport.park();
                System.out.println(PrintHelper.printThreadMark() + "C");
                LockSupport.unpark(thread1);
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
