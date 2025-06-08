package com.mss.thread.sync2;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.Exchanger;

public class ExchangerTest {
    public static Exchanger<Integer> exchanger = new Exchanger<>();

    static class TaskA implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(PrintHelper.printThreadMark() + "交换前的数据：" + i);
                    int data = exchanger.exchange(i);
                    SleepHelper.sleep(1000);
                    System.out.println(PrintHelper.printThreadMark() + "交换后的数据：" + data);
                } catch (InterruptedException e) {
                    PrintHelper.printExceptionMark(e);
                }
            }
        }
    }

    static class TaskB implements Runnable {
        @Override
        public void run() {
            for (int i = 5; i > 0; i--) {
                try {
                    System.out.println(PrintHelper.printContentByRed(PrintHelper.printThreadMark() + "交换前的数据：" + i));
                    int data = exchanger.exchange(i);
                    SleepHelper.sleep(1000);
                    System.out.println(PrintHelper.printContentByRed(PrintHelper.printThreadMark() + "交换后的数据：" + data));
                } catch (InterruptedException e) {
                    PrintHelper.printExceptionMark(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new TaskA()).start();
        new Thread(new TaskB()).start();
    }
}
