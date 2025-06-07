package com.mss.test;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用AtomicInteger实现顺序打印
 */
public class AtomicTest {
    private static final AtomicInteger count = new AtomicInteger(0);

    private volatile boolean flag;

    public void printA() {
        while(!Thread.currentThread().isInterrupted()) {
            if (count.get() == 30) {
                flag = true;
                break;
            }

            if (count.get() % 3 == 0) {
                System.out.println(PrintHelper.printThreadMark() + "=========> Round: " + (count.get() / 3 + 1));
                System.out.println(PrintHelper.printThreadMark() + ": A");
                count.getAndIncrement();
                SleepHelper.sleep(1000);
            }
        }
    }

    public void printB() {
        while(!Thread.currentThread().isInterrupted()) {
            if (flag) {
                break;
            }

            if (count.get() % 3 == 1) {
                System.out.println(PrintHelper.printThreadMark() + ": B");
                count.getAndIncrement();
                SleepHelper.sleep(1000);
            }
        }
    }

    public void printC() {
        while(!Thread.currentThread().isInterrupted()) {
            if (flag) {
                break;
            }

            if (count.get() % 3 == 2) {
                System.out.println(PrintHelper.printThreadMark() + ": C");
                count.getAndIncrement();
                SleepHelper.sleep(1000);
            }
        }
    }

    public static void main(String[] args) {
        AtomicTest atomicTest = new AtomicTest();
        Thread t1 = new Thread(atomicTest::printA);
        Thread t2 = new Thread(atomicTest::printB);
        Thread t3 = new Thread(atomicTest::printC);
        t1.start();
        t2.start();
        t3.start();
    }
}
