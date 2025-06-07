package com.mss.test;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用LockSupport来顺序打印
 */
public class LockSupportTest {
    public static Thread thread1;

    public static Thread thread2;

    public static Thread thread3;

    public static void main(String[] args) {
        thread1 = new Thread(() -> {
            int count = 1;
            while (!Thread.currentThread().isInterrupted()) {
                SleepHelper.sleep(1000);
                System.out.println(PrintHelper.printThreadMark() + "=========> Round: " + count);
                System.out.println(PrintHelper.printThreadMark() + ": A");
                LockSupport.unpark(thread2);
                if (count++ >= 10) {
                    break;
                }
                LockSupport.park();
            }
        });

        thread2 = new Thread(() -> {
            int count = 1;
            while (!Thread.currentThread().isInterrupted()) {
                LockSupport.park();
                System.out.println(PrintHelper.printThreadMark() + ": B");
                LockSupport.unpark(thread3);
                if (count++ >= 10) {
                    break;
                }
            }
        });

        thread3 = new Thread(() -> {
            int count = 1;
            while (!Thread.currentThread().isInterrupted()) {
                LockSupport.park();
                System.out.println(PrintHelper.printThreadMark() + ": C");
                LockSupport.unpark(thread1);
                if (count++ >= 10) {
                    break;
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
