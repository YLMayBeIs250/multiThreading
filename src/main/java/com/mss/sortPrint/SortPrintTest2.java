package com.mss.sortPrint;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SortPrintTest2 {
    public static Lock lock = new ReentrantLock();

    public static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        // 知识点1：使用ReentrantLock的一个Condition实现线程顺序打印ABC，并循环10次
        // FixMe:和直觉想的不一样，signal后，B与C应该争抢锁才对，结果B与C并没有争抢锁，而是按阻塞队列的顺序一次执行，待分析AQS的Condition内部类
        System.out.println(PrintHelper.printThreadMark() + "[使用ReentrantLock的一个Condition实现线程顺序打印ABC，并循环10次]");
        new Thread(() -> {
            try {
                lock.lock();
                int count = 1;
                while (true) {
                    SleepHelper.sleep(1000);
                    System.out.println(PrintHelper.printThreadMark() + "==>" + count++);
                    System.out.println(PrintHelper.printThreadMark() + "A");
                    condition.signal();
                    if (count <= 10)
                        condition.await();
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
                    condition.signal();
                    if (count++ < 10)
                        condition.await();
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
                    condition.signal();
                    if (count++ < 10)
                        condition.await();
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
