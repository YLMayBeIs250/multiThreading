package com.mss.utils;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    /**
     * 获取当前时间的毫秒数。
     *
     * @return 当前时间的毫秒数，返回的是一个long类型的值。
     */
    public static long getCurrentMilliseconds() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间的秒级时间戳
     *
     * @return 当前时间long类型的秒级时间戳
     */
    public static long getCurrentTimestamp() {
        return new Date().getTime() / 1000; // 转换为秒级时间戳
    }

    /**
     * 获取当前日期时间，并按照指定格式（如"yyyy-MM-dd HH:mm:ss.SSS"）进行格式化输出。
     *
     * @return 当前日期时间的字符串表示形式，格式为"yyyy-MM-dd HH:mm:ss.SSS"
     */
    public static String getCurrentFormattedTime() {
        // 获取当前本地日期时间
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 创建一个格式器对象
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        // 格式化当前日期时间
        return currentDateTime.format(formatter);
    }
}
