package com.mss.future;

import com.mss.service.OrderService;
import com.mss.service.UserService;
import com.mss.utils.PrintHelper;
import com.mss.utils.TimeUtils;

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

        FutureTask<String> userServiceFutureTask = new FutureTask<>(userService::getUserServiceResult);
        FutureTask<String> orderServiceFutureTask = new FutureTask<>(orderService::getOrderServiceResult);

        executorService.submit(userServiceFutureTask);
        executorService.submit(orderServiceFutureTask);

        System.out.println(TimeUtils.getCurrentFormattedTime() + PrintHelper.printThreadMark() + " do work1...");
        System.out.println(TimeUtils.getCurrentFormattedTime() + " User Service Result: " + userServiceFutureTask.get());
        System.out.println(TimeUtils.getCurrentFormattedTime() + " Order Service Result: " + orderServiceFutureTask.get());
        System.out.println(TimeUtils.getCurrentFormattedTime() + PrintHelper.printThreadMark() + " do work2...");

        executorService.shutdown();
    }
}
