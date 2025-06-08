package com.mss.thread.sync2;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    static class Task implements Runnable {
        private final CyclicBarrier cyclicBarrier;
        private final String taskName;

        public Task(CyclicBarrier cyclicBarrier, String taskName) {
            this.cyclicBarrier = cyclicBarrier;
            this.taskName = taskName;
        }

        public void run() {
            try {
                System.out.println(PrintHelper.printThreadMark() + "plan for " + taskName + "...");
                cyclicBarrier.await();
                switch (taskName) {
                    case "TaskA":
                        SleepHelper.sleep(2000);
                        break;
                    case "TaskB":
                        SleepHelper.sleep(1000);
                        break;
                    case "TaskC":
                        SleepHelper.sleep(3000);
                        break;
                }
                System.out.println(PrintHelper.printThreadMark() + taskName + " done.");
            } catch (InterruptedException | BrokenBarrierException e) {
                PrintHelper.printExceptionMark(e);
            }
        }
    }

    public static void main(String[] args) {
        // 知识点1：使用CyclicBarrier，让所有线程都执行await，也就是所有线程都准备好了之后，再去执行await后面的逻辑
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,
                () -> System.out.println(PrintHelper.printThreadMark() + "all task is ready, do next thing..."));

        // 模拟先后启动顺序
        new Thread(new Task(cyclicBarrier, "TaskA")).start();
        new Thread(new Task(cyclicBarrier, "TaskB")).start();
        new Thread(new Task(cyclicBarrier, "TaskC")).start();
    }
}
