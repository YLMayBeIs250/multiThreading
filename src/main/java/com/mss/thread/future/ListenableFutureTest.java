package com.mss.thread.future;

import com.google.common.util.concurrent.*;
import com.google.common.util.concurrent.MoreExecutors;
import com.mss.thread.service.ExceptionService;
import com.mss.utils.PrintHelper;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListenableFutureTest {
    public static void main(String[] args) {
        ExceptionService listenableService = new ExceptionService(0);

        // 知识点1：ListenableFuture是可以监听的Future，任务完成就自动调用回调函数，在调用回调函数之前，首先需要实例化ListenableFuture实例对象
        ExecutorService callBackExecutorService = Executors.newFixedThreadPool(2);
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(callBackExecutorService);
        ListenableFuture<Integer> listenableFuture = listeningExecutorService.submit(listenableService);

        // 知识点2：通过ListenableFuture的addListener方法实现回调，不阻塞
        System.out.println(PrintHelper.printThreadMark() + "do step1...");
        listenableFuture.addListener(() -> {
            try {
                System.out.println("使用addListener回调取得结果：" + listenableFuture.get());
            }catch (InterruptedException | ExecutionException e) {
                PrintHelper.printExceptionMark(e);
            }
        }, listeningExecutorService);
        System.out.println(PrintHelper.printThreadMark() + "do step2...");

        // 知识点3：通过Futures的静态方法addCallback给ListenableFuture添加回调函数
        System.out.println(PrintHelper.printThreadMark() + "do step3...");
        Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                System.out.println(PrintHelper.printThreadMark() + "result:" + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(PrintHelper.printThreadMark() + "执行发生异常");
                System.out.println(PrintHelper.printThreadMark() + "异常消息内容：" + t.getMessage());
                System.exit(1);
            }
        }, callBackExecutorService);
        System.out.println(PrintHelper.printThreadMark() + "do step4...");

        // 知识点4：推荐使用第二种方法，因为第二种方法可以直接得到Future的返回值，或者处理错误情况。本质上第二种方法是通过调动第一种方法实现的，做了进一步的封装。
        // ==>另外ListenableFuture还有其他几种内置实现：
        //      SettableFuture：不需要实现一个方法来计算返回值，而只需要返回一个固定值来做为返回值，可以通过程序设置此Future的返回值或者异常信息
        //      CheckedFuture： 这是一个继承自ListenableFuture接口，他提供了checkedGet()方法，此方法在Future执行发生异常时，可以抛出指定类型的异常
        //      参考链接：https://www.cnblogs.com/seedss/p/12762209.html
    }
}
