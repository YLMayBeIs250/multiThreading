package com.mss.sync1;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitTest {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    static class ThreadA implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println(PrintHelper.printThreadMark() + "start A task...");
                condition.await();
                SleepHelper.sleep(2000);
                System.out.println(PrintHelper.printThreadMark() + "A task done.");
            } catch (InterruptedException e) {
                PrintHelper.printExceptionMark(e);
            } finally {
                lock.unlock();
            }
        }
    }

    static class ThreadB implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println(PrintHelper.printThreadMark() + "start B task...");
                condition.signal();
                SleepHelper.sleep(3000);
                System.out.println(PrintHelper.printThreadMark() + "B task done.");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        // 知识点1：使用await()和signal()/signalAll()方法实现线程的等待和唤醒，比wait方法更好
        new Thread(new ThreadA()).start();
        new Thread(new ThreadB()).start();
    }
}
