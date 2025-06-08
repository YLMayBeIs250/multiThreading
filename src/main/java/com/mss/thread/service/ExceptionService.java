package com.mss.thread.service;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;


public class ExceptionService implements Callable<Integer> {
    private final AtomicInteger count;

    public ExceptionService(int count) {
        this.count = new AtomicInteger(count);
    }

    /**
     * 模拟一个包含异常的业务
     * @return
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        while (count.get() < 10) {
            count.getAndIncrement();
            System.out.println(PrintHelper.printThreadMark() + "count num:" + count.get());
            SleepHelper.sleep(500);
            if (count.get() == 5) {
                throw new InterruptedException("发生了InterruptedException异常.");
            }
        }
        return count.get();
    }
}
