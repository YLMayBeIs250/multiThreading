package com.mss.sortPrint;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class SortPrintTest1 {

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {

        // 知识点1：使用AtomicInteger实现线程顺序打印ABC，并循环10次
        System.out.println(PrintHelper.printThreadMark() + "[使用AtomicInteger实现线程顺序打印ABC，并循环100次]");
        Thread thread1 = new Thread(() -> {
            int count = 1;
            while (!Thread.currentThread().isInterrupted()) {
                if (atomicInteger.get() % 3 == 0) {
                    System.out.println(PrintHelper.printThreadMark() + "==> " + count++);
                    System.out.println(PrintHelper.printThreadMark() + "A");
                    atomicInteger.getAndIncrement();
                }
                if (count == 11) {
                    break;
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (atomicInteger.get() % 3 == 1) {
                    System.out.println(PrintHelper.printThreadMark() + "B");
                    atomicInteger.getAndIncrement();
                }
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (atomicInteger.get() % 3 == 2) {
                    System.out.println(PrintHelper.printThreadMark() + "C");
                    atomicInteger.getAndIncrement();
                }
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        while (true) {
            if (atomicInteger.get() == 30) {
                thread1.interrupt();
                thread2.interrupt();
                thread3.interrupt();
                break;
            }
            SleepHelper.sleep(1000);
        }
    }
}
