package com.mss.thread.sortPrint;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SortPrintTest3 {
    public static Lock lock = new ReentrantLock();

    public static Condition condition1 = lock.newCondition();

    public static Condition condition2 = lock.newCondition();

    public static Condition condition3 = lock.newCondition();

    public static void main(String[] args) {
        // 知识点1：使用ReentrantLock的多个Condition实现线程顺序打印ABC，并循环10次
        // 该实现效果同SortPrint2一模一样
        System.out.println(PrintHelper.printThreadMark() + "[使用ReentrantLock的多个Condition实现线程顺序打印ABC，并循环10次]");
        new Thread(() -> {
            try {
                lock.lock();
                int count = 1;
                while (true) {
                    SleepHelper.sleep(1000);
                    System.out.println(PrintHelper.printThreadMark() + "==>" + count++);
                    System.out.println(PrintHelper.printThreadMark() + "A");
                    condition2.signal();
                    if (count <= 10)
                        condition1.await();
                    else
                        break;
                }
            } catch (Exception e) {
                PrintHelper.printExceptionMark(e);
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();
                int count = 1;
                while (true) {
                    System.out.println(PrintHelper.printThreadMark() + "B");
                    condition3.signal();
                    if (count++ < 10)
                        condition2.await();
                    else
                        break;
                }
            } catch (Exception e) {
                PrintHelper.printExceptionMark(e);
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();
                int count = 1;
                while (true) {
                    System.out.println(PrintHelper.printThreadMark() + "C");
                    condition1.signal();
                    if (count++ < 10)
                        condition3.await();
                    else
                        break;
                }
            } catch (Exception e) {
                PrintHelper.printExceptionMark(e);
            } finally {
                lock.unlock();
            }
        }).start();
    }
}
