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
        // 知识点2：AQS内部有一个阻塞队列和N个条件队列，一个Condition对象就是一个条件队列，队列都是FIFO类型的，因此先进入条件队列的会先进入阻塞队列等待获得执行
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
