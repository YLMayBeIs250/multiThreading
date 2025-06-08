package com.mss.thread.future;

import com.mss.thread.service.OrderService;
import com.mss.thread.service.UserService;
import com.mss.utils.PrintHelper;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class CompletableFutureTest {
    public static void main(String[] args) {
        UserService userService = new UserService();
        OrderService orderService = new OrderService();

        // 知识点1：使用CompletableFuture的静态方法supplyAsync，传递一个Supplier<U>参数
        CompletableFuture<String> userServiceCompletableFuture = CompletableFuture.supplyAsync(userService::getUserServiceResult);
        CompletableFuture<String> orderServiceCompletableFuture = CompletableFuture.supplyAsync(orderService::getOrderServiceResult);

        // 知识点2：使用userServiceCompletableFuture的thenApply作为回调方法，接受一个Function<T,U>参数，不阻塞
        System.out.println(PrintHelper.printThreadMark() + "do step1...");
        userServiceCompletableFuture.thenApply(s -> "userService thenApply callBack function...");
        orderServiceCompletableFuture.thenApply(s -> "orderService thenApply callBack  function...");
        System.out.println(PrintHelper.printThreadMark() + "do step2...");

        // 知识点3：使用userServiceCompletableFuture的thenAccept作为回调方法，接受一个Consumer<? super T>参数，不阻塞
        System.out.println(PrintHelper.printThreadMark() + "do step3...");
        userServiceCompletableFuture.thenAccept(s -> System.out.println(PrintHelper.printThreadMark() + "userService thenAccept callBack function..."));
        orderServiceCompletableFuture.thenAccept(s -> System.out.println(PrintHelper.printThreadMark() + "orderService thenAccept callBack function..."));
        System.out.println(PrintHelper.printThreadMark() + "do step4...");

        // 知识点4：使用userServiceCompletableFuture的thenRun作为回调方法，接受一个Runnable参数，不阻塞
        System.out.println(PrintHelper.printThreadMark() + "do step5...");
        userServiceCompletableFuture.thenRun(() -> System.out.println(PrintHelper.printThreadMark() + "userService thenRun callBack function..."));
        orderServiceCompletableFuture.thenRun(() -> System.out.println(PrintHelper.printThreadMark() + "orderService thenRun callBack function..."));
        System.out.println(PrintHelper.printThreadMark() + "do step6...");

        // 知识点5：使用CompletableFuture的静态方法supplyAsync，传递一个Supplier<U>参数和自定义线程池
        CompletableFuture<String> userServiceCompletableFuture2 =
                CompletableFuture.supplyAsync(userService::getUserServiceResult, Executors.newFixedThreadPool(1));
        CompletableFuture<String> orderServiceCompletableFuture2 =
                CompletableFuture.supplyAsync(orderService::getOrderServiceResult, Executors.newFixedThreadPool(1));
        System.out.println(PrintHelper.printThreadMark() + "do step7...");
        userServiceCompletableFuture.thenApply(s -> "userService thenApply callBack function...");
        orderServiceCompletableFuture.thenApply(s -> "orderService thenApply callBack  function...");
        System.out.println(PrintHelper.printThreadMark() + "do step8...");
    }
}
