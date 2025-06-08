package com.mss.thread.test;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用单个Condition来实现顺序打印
 */
public class SingleConditionTest {
    private static final Lock lock = new ReentrantLock();

    private static final Condition condition = lock.newCondition();

    public void printA() {
        int count = 1;
        while(!Thread.currentThread().isInterrupted()) {
            try {
                lock.lock();
                SleepHelper.sleep(1000);
                System.out.println(PrintHelper.printThreadMark() + "=========> Round: " + count);
                System.out.println(PrintHelper.printThreadMark() + ": A");
                condition.signal();
                if (count++ < 10) {
                    condition.await();
                } else {
                    break;
                }
            } catch (InterruptedException e) {
                PrintHelper.printExceptionMark(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public void printB() {
        int count = 1;
        while(!Thread.currentThread().isInterrupted()) {
            try {
                lock.lock();
                System.out.println(PrintHelper.printThreadMark() + ": B");
                condition.signal();
                if (count++ < 10) {
                    condition.await();
                } else {
                    break;
                }
            } catch (InterruptedException e) {
                PrintHelper.printExceptionMark(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public void printC() {
        int count = 1;
        while(!Thread.currentThread().isInterrupted()) {
            try {
                lock.lock();
                SleepHelper.sleep(100);
                System.out.println(PrintHelper.printThreadMark() + ": C");
                condition.signal();
                if (count++ < 10) {
                    condition.await();
                } else {
                    break;
                }
            } catch (InterruptedException e) {
                PrintHelper.printExceptionMark(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        SingleConditionTest test = new SingleConditionTest();
        Thread t1 = new Thread(test::printA);
        Thread t2 = new Thread(test::printB);
        Thread t3 = new Thread(test::printC);
        t1.start();
        t2.start();
        t3.start();
    }
}
