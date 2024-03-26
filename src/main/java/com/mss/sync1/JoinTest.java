package com.mss.sync1;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

public class JoinTest {
    static class ThreadA extends Thread {
        private final Thread threadB;

        public ThreadA(Thread thread) {
            threadB = thread;
        }

        @Override
        public void run() {
            try {
                System.out.println(PrintHelper.printThreadMark() + "start A task...");
                SleepHelper.sleep(2000);
                threadB.join();
                System.out.println(PrintHelper.printThreadMark() + "A task done.");
            } catch (InterruptedException e) {
                PrintHelper.printExceptionMark(e);
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            System.out.println(PrintHelper.printThreadMark() + "start B task...");
            SleepHelper.sleep(3000);
            System.out.println(PrintHelper.printThreadMark() + "B task done.");
        }
    }

    static class ThreadC extends Thread {
        private Thread threadD = null;

        public void setThread(Thread thread) {
            threadD = thread;
        }

        @Override
        public void run() {
            try {
                System.out.println(PrintHelper.printThreadMark() + "start C task...");
                SleepHelper.sleep(2000);
                threadD.join();
                System.out.println(PrintHelper.printThreadMark() + "C task done.");
            } catch (InterruptedException e) {
                PrintHelper.printExceptionMark(e);
            }
        }
    }

    static class ThreadD extends Thread {
        private Thread threadC = null;

        public void setThread(Thread thread) {
            threadC = thread;
        }

        @Override
        public void run() {
            try {
                System.out.println(PrintHelper.printThreadMark() + "start D task...");
                SleepHelper.sleep(2000);
                threadC.join();
                System.out.println(PrintHelper.printThreadMark() + "D task done.");
            } catch (InterruptedException e) {
                PrintHelper.printExceptionMark(e);
            }
        }
    }

    public static void main(String[] args) {
        // 知识点1：即使是A线程先启动，且A线程任务耗时更短，但是join会让B线程先执行完
        ThreadB threadB = new ThreadB();
        ThreadA threadA = new ThreadA(threadB);
        threadA.start();
        threadB.start();

        // 知识点2：join操作会造成死锁，比如A线程的run方法里面调用了自己线程的join方法，或者如下，互相调用对方线程的join方法
        ThreadC threadC = new ThreadC();
        ThreadD threadD = new ThreadD();
        threadD.setThread(threadC);
        threadC.setThread(threadD);
        threadC.start();
        threadD.start();
        SleepHelper.sleep(10000);
        System.out.println(PrintHelper.printThreadMark() + "C和D的task发生了死锁，task一直不会打印done...");
    }
}
