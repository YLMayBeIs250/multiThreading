package com.mss.utils;

public class SleepHelper {

    /**
     * 使当前线程睡眠指定的毫秒数。
     *
     * @param millisecond 线程睡眠的毫秒数。
     * 该方法会尝试让当前线程睡眠指定的时间，如果在睡眠期间线程被中断，则会捕获到InterruptedException异常，并恢复线程的中断状态。
     */
    public static void sleep(long millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            PrintHelper.printExceptionMark(e);
            // 恢复中断状态
            Thread.currentThread().interrupt();
        }
    }
}
