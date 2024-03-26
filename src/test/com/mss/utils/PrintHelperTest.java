package com.mss.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintHelperTest {

    @Test
    public void testPrintThreadMark() {
        // 执行待测方法
        String threadMark = PrintHelper.printThreadMark();
        System.out.println(threadMark);

        // 验证返回的字符串包含当前时间 and 当前线程名
        assertTrue(threadMark.contains("["), "The string should contain a time bracket.");
        assertTrue(threadMark.contains("]"), "The string should contain a closing time bracket.");
        assertTrue(threadMark.contains("[" + Thread.currentThread().getName() + "]"),
                "The string should contain the current thread name in brackets.");
    }

    @Test
    void testPrintExceptionMarkWithRuntimeException() {
        RuntimeException runtimeException = new RuntimeException("RuntimeException for testing");
        String result = PrintHelper.printExceptionMark(runtimeException);
        assertEquals(">>> 捕获到 [java.lang.RuntimeException] 异常.", result);
    }

    @Test
    void testPrintExceptionMarkWithIOException() {
        IOException ioException = new IOException("IOException for testing");
        String result = PrintHelper.printExceptionMark(ioException);
        assertEquals(">>> 捕获到 [java.io.IOException] 异常.", result);
    }

    @Test
    void testPrintExceptionMarkWithNullPointerException() {
        NullPointerException nullPointerException = new NullPointerException("NullPointerException for testing");
        String result = PrintHelper.printExceptionMark(nullPointerException);
        assertEquals(">>> 捕获到 [java.lang.NullPointerException] 异常.", result);
    }
    @Test
    public void testPrintContentByRed() {
        String expected = "\u001B[31mHello World\u001B[0m";
        String actual = PrintHelper.printContentByRed("Hello World");
        System.out.println(actual);
        assertEquals(expected, actual, "The red colored string does not match the expected output.");
    }


}
