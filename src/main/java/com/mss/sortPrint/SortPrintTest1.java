package com.mss.sortPrint;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class SortPrintTest1 {

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {

        // 知识点1：使用AtomicInteger实现线程顺序打印ABC，并循环10次
        System.out.println(PrintHelper.printThreadMark() + "[使用AtomicInteger实现线程顺序打印ABC，并循环10次]");
        Thread thread1 = new Thread(() -> {
            int count = 1;
            while (!Thread.currentThread().isInterrupted()) {
                // 注意：这里可以简单使用打印次数count计数来实现线程退出
                // 这里在main线程使用thread1.interrupt()来实现线程退出主要是为了复习一下interrupt()的知识
                SleepHelper.sleep(1000);
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                if (atomicInteger.get() % 3 == 0) {
                    System.out.println(PrintHelper.printThreadMark() + "==> " + count++);
                    System.out.println(PrintHelper.printThreadMark() + "A");
                    atomicInteger.getAndIncrement();
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

        // 知识点2：使用interrupt()来中断线程
        // 注意，由于thread1先启动，如果main线程睡眠时间设置为1000ms，那么设置中断信号会晚几毫秒，那么线程1会多打印一个A
        // 可以先SleepHelper.sleep(1000)，然后设置atomicInteger.get() == 30，main线程中断信号会晚一点，线程1会先打印一个A，验证上述描述
        while (true) {
            SleepHelper.sleep(500);
            if (atomicInteger.get() == 30) {
                thread1.interrupt();
                thread2.interrupt();
                thread3.interrupt();
                break;
            }
        }
    }
}
