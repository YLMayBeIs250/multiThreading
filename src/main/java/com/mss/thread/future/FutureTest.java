package com.mss.thread.future;

import com.mss.thread.service.OrderService;
import com.mss.thread.service.UserService;
import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.*;

public class FutureTest {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    /**
     * get阻塞获取参数
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UserService userService = new UserService();
        OrderService orderService = new OrderService();

        // 知识点1：FutureTask可接受一个实现了Callable接口的参数，并返回一个Future对象（下面是lambda表达式改为方法引用）
        FutureTask<String> userServiceFutureTask = new FutureTask<>(userService::getUserServiceResult);
        FutureTask<String> orderServiceFutureTask = new FutureTask<>(orderService::getOrderServiceResult);
        executorService.submit(userServiceFutureTask);
        executorService.submit(orderServiceFutureTask);
        System.out.println(PrintHelper.printThreadMark() + "do step1...");
        System.out.println(PrintHelper.printThreadMark() + userServiceFutureTask.get());
        System.out.println(PrintHelper.printThreadMark() + orderServiceFutureTask.get());
        // 可以看到do step2是被阻塞的，要等上面两个get方法都返回后才会执行
        System.out.println(PrintHelper.printThreadMark() + "do step2...");

        // 知识点2：FutureTask可接受一个实现了Runnable接口的参数和一个result参数，该参数就是futureTask.get()的值
        FutureTask<String> userServiceFutureTask2 = new FutureTask<>(userService::startUserService, "userServiceFutureTask2 done.");
        FutureTask<String> orderServiceFutureTask2 = new FutureTask<>(orderService::startOrderService, "orderServiceFutureTask2 done.");
        executorService.submit(userServiceFutureTask2);
        executorService.submit(orderServiceFutureTask2);
        System.out.println(PrintHelper.printThreadMark() + "do step3...");
        System.out.println(PrintHelper.printThreadMark() + userServiceFutureTask2.get());
        System.out.println(PrintHelper.printThreadMark() + orderServiceFutureTask2.get());
        // 可以看到do step4是被阻塞的，要等上面两个get方法都返回后才会执行
        System.out.println(PrintHelper.printThreadMark() + "do step4...");

        // 知识点3：FutureTask的isDone方法可以判断任务是否完成，轮询（资源利用率低）
        FutureTask<String> orderServiceFutureTask3 = new FutureTask<>(orderService::getOrderServiceResult);
        executorService.submit(orderServiceFutureTask3);
        while (!orderServiceFutureTask3.isDone()) {
            System.out.println(PrintHelper.printThreadMark() + "orderServiceFutureTask3 not done...");
            SleepHelper.sleep(1000);
        }
        System.out.println(PrintHelper.printThreadMark() + "orderServiceFutureTask3 done.");

        executorService.shutdown();
    }
}
