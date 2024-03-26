package com.mss.sync2;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;
;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    static class Task implements Runnable {
        private final Semaphore semaphore;
        private final String taskName;

        public Task(Semaphore semaphore, String taskName) {
            this.semaphore = semaphore;
            this.taskName = taskName;
        }

        public void run() {
            boolean result = semaphore.tryAcquire();
            if (result) {
                System.out.println(PrintHelper.printThreadMark() + "get the semaphore, start " + taskName + "...");
                SleepHelper.sleep(2000);
            } else {
                System.out.println(PrintHelper.printThreadMark() + "can't get the semaphore, stop " + taskName + ".");
            }
        }
    }

    public static void main(String[] args) {
        // 知识点1：使用Semaphore来控制线程并发
        Semaphore semaphore1 = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            new Thread(new Task(semaphore1, "task" + (i + 1))).start();
        }

        // 知识点2：tryAcquire可以一次性获取多个信号量，release也可以一次性释放多个信号量
        Semaphore semaphore2 = new Semaphore(5);
        new Thread(() -> {
            boolean result = semaphore2.tryAcquire(5);
            if (result) {
                System.out.println(PrintHelper.printThreadMark() + "tryAcquire 5 success...");
            } else {
                System.out.println(PrintHelper.printThreadMark() + "tryAcquire 5 failed...");
            }
            SleepHelper.sleep(2000);
            semaphore2.release(6);
            System.out.println(PrintHelper.printThreadMark() + "semaphore2 left " + semaphore2.availablePermits());
        }).start();
    }
}
