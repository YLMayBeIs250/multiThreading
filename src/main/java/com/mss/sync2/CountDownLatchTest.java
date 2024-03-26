package com.mss.sync2;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) {
        // 知识点1：创建CountDownLatch传入一个数值，表示需要等待的线程数量，
        // countDownLatch.countDown()使等待数减一，countDownLatch.await()代表要等待其他线程执行减一动作到0后开始执行，不然阻塞
        CountDownLatch countDownLatch = new CountDownLatch(3);
        System.out.println(PrintHelper.printThreadMark() + "start main task...");
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println(PrintHelper.printThreadMark() + "start task...");
                SleepHelper.sleep(1000);
                System.out.println(PrintHelper.printThreadMark() + "task done.");
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
            System.out.println(PrintHelper.printThreadMark() + "main task done.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
