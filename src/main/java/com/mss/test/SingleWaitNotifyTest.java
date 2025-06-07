package com.mss.test;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

/**
 * 使用Object的wait、notify来实现顺序打印(仅支持2个线程)
 */
public class SingleWaitNotifyTest {
    private static final Object object = new Object();

    public void printA() {
        int count = 1;
        synchronized (object) {
            while (!Thread.currentThread().isInterrupted()) {
                SleepHelper.sleep(1000);
                System.out.println(PrintHelper.printThreadMark() + "=========> Round: " + count);
                System.out.println(PrintHelper.printThreadMark() + ": A");
                object.notify();
                try {
                    if (count++ < 10) {
                        object.wait();
                    } else {
                        break;
                    }
                } catch (InterruptedException e) {
                    PrintHelper.printExceptionMark(e);
                }
            }
        }
    }

    public void printB() {
        int count = 1;
        synchronized (object) {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(PrintHelper.printThreadMark() + ": B");
                object.notify();
                try {
                    if (count++ < 10) {
                        object.wait();
                    } else {
                        break;
                    }
                } catch (InterruptedException e) {
                    PrintHelper.printExceptionMark(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        SingleWaitNotifyTest test = new SingleWaitNotifyTest();
        Thread t1 = new Thread(test::printA);
        Thread t2 = new Thread(test::printB);
        t1.start();
        t2.start();
    }
}
