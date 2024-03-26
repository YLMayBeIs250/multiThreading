package com.mss.utils;

public class PrintHelper {

    /**
     * 打印当前线程标记。
     * <p>该方法会返回一个字符串，包含当前的时间和线程名称，用于标识当前操作的时间和执行线程。</p>
     *
     * @return 返回格式化的时间和当前线程名称的字符串，格式为"[当前时间] [当前线程名]"。
     */
    public static String printThreadMark() {
        return "[" + TimeUtils.getCurrentFormattedTime() + "] [" + Thread.currentThread().getName() + "] ";
    }

    /**
     * 打印异常标记。
     *
     * @param e 异常对象，代表发生异常的实例。
     * @return 返回一个字符串，该字符串指明捕获到了哪种异常。
     */
    public static String printExceptionMark(Exception e) {
        String exceptionName = e.getClass().getName();
        return printContentByRed(">>> 捕获到 [" + exceptionName + "] 异常.");
    }

    /**
     * 用红色字体打印
     * @param message
     * @return
     */
    public static String printContentByRed(String message) {
        return "\u001B[31m" + message + "\u001B[0m";
    }
}
