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
            // 知识点1：线程实例的interrupt()只是给线程打上中断标记，由于这里捕获到了中断异常，为了让中断标记传递下去，因此调用了interrupt()
            // 知识点2：线程实例的interrupted()查询当前线程是否已中断，此方法清除线程的中断状态，换句话说，如果连续调用此方法两次，则第二次调用将返回false
            // 知识点3：线程实例的isInterrupted方法是查询此线程的线程状态（如果此线程被打断时，线程处于非阻塞态，会返回true，处于阻塞态，会返回false），这也是下面为什么要传递中断标记的原因
            // 由于sleep是一个阻塞过程，在这个过程中被中断的话，线程实例的isInterrupted会返回false，因此为了传递这个中断状态可以被感知到，需要调用Thread.currentThread().interrupt()来恢复中断状态
            Thread.currentThread().interrupt();
        }
    }
}
