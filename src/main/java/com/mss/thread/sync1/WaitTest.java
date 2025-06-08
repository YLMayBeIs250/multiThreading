package com.mss.thread.sync1;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

public class WaitTest {
    public synchronized void doTaskA() {
        try {
            System.out.println(PrintHelper.printThreadMark() + "do task A...");
            wait();
            SleepHelper.sleep(2000);
            System.out.println(PrintHelper.printThreadMark() + "A task done.");
        } catch (InterruptedException e) {
            PrintHelper.printExceptionMark(e);
        }
    }

    public synchronized void doTaskB() {
        System.out.println(PrintHelper.printThreadMark() + "do task B...");
        notify();
        SleepHelper.sleep(2000);
        System.out.println(PrintHelper.printThreadMark() + "B task done.");
    }

    public static void main(String[] args) {
        WaitTest waitTest = new WaitTest();
        // 知识点1：wait()和notify()方法必须在同步块或同步方法中（synchronized修饰）调用，以避免IllegalMonitorStateException异常
        // ==> wait方法会释放锁，进入等待状态，notify方法会唤醒等待线程，唤醒线程不是立马执行，而是获得CPU时间片后执行
        new Thread(waitTest::doTaskA).start();
        new Thread(waitTest::doTaskB).start();
    }
}
