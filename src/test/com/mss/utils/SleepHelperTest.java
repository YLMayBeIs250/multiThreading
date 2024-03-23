package com.mss.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

public class SleepHelperTest {

    @BeforeEach
    public void setUp() {
        // 在每个测试之前执行的代码（如果需要）
    }

    @AfterEach
    public void tearDown() {
        // 在每个测试之后执行的代码（如果需要）
    }

    @Test
    public void testSleepDoesNotThrowException() {
        // 测试睡眠方法没有抛出异常
        long startTime = System.currentTimeMillis();
        SleepHelper.sleep(1000);
        long endTime = System.currentTimeMillis();

        // 确保至少睡眠了指定的时间
        Assertions.assertTrue(endTime - startTime >= 1000);
    }

    @Test
    public void testSleepHandlesInterruptedException() throws InterruptedException {
        // 测试睡眠方法是否正确处理InterruptedException

        // 设置当前线程为中断状态
        Thread.currentThread().interrupt();

        long startTime = System.currentTimeMillis();
        SleepHelper.sleep(1000);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        // 由于线程被中断，sleep方法应该会立即返回
        Assertions.assertTrue(endTime - startTime < 1000);

        // 验证线程的中断状态是否仍然设置
        Assertions.assertTrue(Thread.currentThread().isInterrupted());
    }
}
