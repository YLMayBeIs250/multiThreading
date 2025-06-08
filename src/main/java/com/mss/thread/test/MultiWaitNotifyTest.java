package com.mss.thread.test;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

/**
 * 使用多个Object的wait、notify来实现顺序打印(支持3个线程)
 */
public class MultiWaitNotifyTest {
    private static final Object object1 = new Object();

    private static final Object object2 = new Object();

    private static final Object object3 = new Object();

    public void printA() {
        int count = 1;
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (object1) {
                synchronized (object2) {
                    SleepHelper.sleep(1000);
                    System.out.println(PrintHelper.printThreadMark() + "=========> Round: " + count);
                    System.out.println(PrintHelper.printThreadMark() + ": A");
                    object2.notify();
                }
                try {
                    if (count++ < 10) {
                        object1.wait();
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
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (object2) {
                synchronized (object3) {
                    System.out.println(PrintHelper.printThreadMark() + ": B");
                    object3.notify();
                }
                try {
                    if (count++ < 10) {
                        object2.wait();
                    } else {
                        break;
                    }
                } catch (InterruptedException e) {
                    PrintHelper.printExceptionMark(e);
                }
            }
        }
    }

    public void printC() {
        int count = 1;
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (object3) {
                try {
                    if (count++ < 11) {
                        object3.wait();
                    } else {
                        break;
                    }
                } catch (InterruptedException e) {
                    PrintHelper.printExceptionMark(e);
                }
                synchronized (object1) {
                    System.out.println(PrintHelper.printThreadMark() + ": C");
                    object1.notify();
                }
            }
        }
    }

    public static void main(String[] args) {
        MultiWaitNotifyTest test = new MultiWaitNotifyTest();
        Thread t1 = new Thread(test::printA);
        Thread t2 = new Thread(test::printB);
        Thread t3 = new Thread(test::printC);
        t1.start();
        t2.start();
        t3.start();
    }
}
