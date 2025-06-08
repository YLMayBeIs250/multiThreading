package com.mss.thread.sortPrint;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

public class SortPrintTest4 {
    public static final Object object = new Object();

    public static void main(String[] args) {
        // 知识点1：使用单个Object对象锁 + wait、notify实现顺序打印十次AB（无法3线程协作）
        System.out.println(PrintHelper.printThreadMark() + "[使用单个Object对象锁 + wait、notify实现顺序打印十次AB]");
        new Thread(() -> {
            synchronized (object) {
                for (int i = 1; i < 11; i++) {
                    SleepHelper.sleep(1000);
                    System.out.println(PrintHelper.printThreadMark() + "==>" + i);
                    System.out.println(PrintHelper.printThreadMark() + "A");
                    object.notify();
                    try {
                        if (i < 10)
                            object.wait();
                        else
                            break;
                    } catch (InterruptedException e) {
                        PrintHelper.printExceptionMark(e);
                    }
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (object) {
                for (int i = 1; i < 11; i++) {
                    System.out.println(PrintHelper.printThreadMark() + "B");
                    object.notify();
                    try {
                        if (i < 10)
                            object.wait();
                        else
                            break;
                    } catch (InterruptedException e) {
                        PrintHelper.printExceptionMark(e);
                    }
                }
            }
        }).start();
    }
}
