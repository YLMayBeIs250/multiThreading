package com.mss.thread;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

import java.util.concurrent.*;

public class CreateThreadPoolTest {
    public static void main(String[] args) {
        // 知识点1：使用Executors工厂方法可以创建以下四种类型的线程池（），但是这种用法有以下几个问题
        // ==> 存在的问题：
        //      1，异常处理：当线程池中的线程执行任务时，如果任务抛出未捕获的异常，线程池默认会将这个异常吞掉。
        //      这可能会导致一些错误难以被发现和追踪。需要确保任务中有合适的异常处理，或者配置线程池来处理未捕获的异常。
        //      2，等待队列未设置参数，也就是会形成无限长的队列，导致内存OOM
        ExecutorService executorService1 = Executors.newFixedThreadPool(10);
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();
        ScheduledExecutorService executorService4 = Executors.newScheduledThreadPool(10);

        // 知识点2：使用new ThreadPoolExecutor来创建线程池
        // corePoolSize，线程池的核心线程数，即线程池中保持活动的最小线程数。
        // maximumPoolSize，线程池的最大线程数，即线程池中允许的最大线程数。
        // keepAliveTime，非核心线程的空闲时间，超过这个时间，多余的线程会被回收。
        // TimeUnit.MILLISECONDS：时间单位，表示 keepAliveTime 的时间单位，这里是毫秒。
        // new LinkedBlockingQueue<Runnable>(1000)：工作队列，用于存放等待执行的任务。这里使用了 LinkedBlockingQueue，并指定了容量为 1000，即最多可以存放 1000 个任务。
        // 当有新任务到来时，如果当前线程池中的线程数量小于核心线程数，则会优先创建新的线程来处理任务，即使有空闲线程存在。
        // 如果当前线程池中的线程数量已经达到核心线程数，并且工作队列未满，新任务会被放入工作队列中等待执行。
        // 当工作队列已满时，如果当前线程池中的线程数量未达到最大线程数，则会创建新的非核心线程来处理任务。
        // 如果当前线程池中的线程数量已经达到最大线程数，并且工作队列也已满，则会根据指定的拒绝策略来处理新任务。
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,
                10,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1000));

        // 知识点3：
        // execute() 方法：
        //      execute() 方法用于向线程池提交实现了 Runnable 接口的任务。
        //      execute() 方法没有返回值，无法获取任务执行的结果或异常信息。
        //      如果任务执行过程中出现异常，线程池会捕获并处理异常，但无法通过 execute() 方法获取到异常信息。
        // submit() 方法：
        //      submit() 方法用于向线程池提交实现了 Callable 接口、Runnable 两个接口的任务，可以获取任务执行的结果。
        //      submit() 方法会返回一个 Future 对象，可以通过 Future 对象获取任务执行的结果或取消任务的执行。
        //      如果任务执行过程中出现异常，可以通过调用 Future 对象的 get() 方法来获取异常信息
        //      如果任务执行过程中出现异常，通过调用 Future 对象的 get() 方法会抛出 ExecutionException 异常
        threadPoolExecutor.execute(() -> {
            System.out.println(PrintHelper.printThreadMark() + "do execute()...");
            SleepHelper.sleep(2000);
            System.out.println(PrintHelper.printThreadMark() + "execute end.");
        });

        Future<?> future = threadPoolExecutor.submit(() -> {
            System.out.println(PrintHelper.printThreadMark() + "do submit()...");
            SleepHelper.sleep(2000);
            System.out.println(PrintHelper.printThreadMark() + "submit end.");
        });
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            PrintHelper.printExceptionMark(e);
        }
    }

}
