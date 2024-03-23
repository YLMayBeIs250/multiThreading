package com.mss.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeUtilsTest {

    @Test
    public void testGetCurrentMilliseconds() {
        // 获取当前时间毫秒数
        long beforeCall = System.currentTimeMillis();
        // 调用待测试的方法
        long result = TimeUtils.getCurrentMilliseconds();
        // 获取调用后的时间毫秒数
        long afterCall = System.currentTimeMillis();

        // 验证返回的时间在当前时间之前或等于当前时间
        assertTrue(result >= beforeCall,
                "The result should be less than or equal to the beforeCall time.");
        assertTrue(result <= afterCall,
                "The result should be greater than or equal to the afterCall time.");
    }

    @Test
    public void testGetCurrentTimeFormatted() {
        String formattedTime = TimeUtils.getCurrentFormattedTime();
        System.out.println(formattedTime);

        assertTrue(formattedTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}"),
                "The formatted time does not match the expected pattern.");
    }

}
