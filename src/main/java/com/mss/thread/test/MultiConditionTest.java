package com.mss.thread.test;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用多个Condition来实现顺序打印
 */
public class MultiConditionTest {
    private static final Lock lock = new ReentrantLock();

    private static final Condition condition1 = lock.newCondition();

    private static final Condition condition2 = lock.newCondition();

    private static final Condition condition3 = lock.newCondition();

    public void printA() {
        int count = 1;
        try {
            lock.lock();
            while(!Thread.currentThread().isInterrupted()) {
                SleepHelper.sleep(1000);
                System.out.println(PrintHelper.printThreadMark() + "=========> Round: " + count);
                System.out.println(PrintHelper.printThreadMark() + ": A");
                condition2.signal();
                if (count++ < 10) {
                    condition1.await();
                } else {
                    break;
                }
            }
        } catch (InterruptedException e) {
            PrintHelper.printExceptionMark(e);
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        int count = 1;
        try {
            lock.lock();
            while(!Thread.currentThread().isInterrupted()) {
                System.out.println(PrintHelper.printThreadMark() + ": B");
                condition3.signal();
                if (count++ < 10) {
                    condition2.await();
                } else {
                    break;
                }
            }
        } catch (InterruptedException e) {
            PrintHelper.printExceptionMark(e);
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        int count = 1;
        try {
            lock.lock();
            while(!Thread.currentThread().isInterrupted()) {
                System.out.println(PrintHelper.printThreadMark() + ": C");
                condition1.signal();
                if (count++ < 10) {
                    condition3.await();
                } else {
                    break;
                }
            }
        } catch (InterruptedException e) {
            PrintHelper.printExceptionMark(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        MultiConditionTest test = new MultiConditionTest();
        Thread t1 = new Thread(test::printA);
        Thread t2 = new Thread(test::printB);
        Thread t3 = new Thread(test::printC);
        t1.start();
        t2.start();
        t3.start();
    }
}
