package com.mss.thread;

import com.mss.service.UserService;
import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class NewThreadTest {
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(PrintHelper.printThreadMark() + "Mythread run...");
            SleepHelper.sleep(2000);
            System.out.println(PrintHelper.printThreadMark() + "Mythread end.");
        }
    }

    public static void main(String[] args) throws Exception {
        UserService userService = new UserService();

        // 知识点1：创建线程的第一种方式，自定义线程类继承Thread类实现run方法
        MyThread thread1 = new MyThread();
        thread1.start();

        // 知识点2：创建线程的第二种方式，实现Runnable接口，实现run方法，作为Thread构造函数的参数
        Thread thread2 = new Thread(() -> {
            System.out.println(PrintHelper.printThreadMark() + "Thread2 run...");
            SleepHelper.sleep(2000);
            System.out.println(PrintHelper.printThreadMark() + "Thread2 end.");
        });
        thread2.start();

        // 知识点3：创建线程的第三种方式，实现Callable接口，实现call方法，用FutureTask包装一下作为Thread构造函数的参数
        Callable<String> callable = userService::getUserServiceResult;
        FutureTask<String> userServiceFutureTask = new FutureTask<>(callable);
        new Thread(userServiceFutureTask).start();

        // 知识点4：Runnable接口或者Callable接口的实例可以直接调用run方法或者call方法，这样的话是在主线程运行
        Runnable runnable2 = () -> {
            System.out.println(PrintHelper.printThreadMark() + "do run()...");
            SleepHelper.sleep(2000);
            System.out.println(PrintHelper.printThreadMark() + "run end.");
        };
        Callable<String> callable2 = userService::getUserServiceResult;
        runnable2.run();
        callable2.call();
    }
}


