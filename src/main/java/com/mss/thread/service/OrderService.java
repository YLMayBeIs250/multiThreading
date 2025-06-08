package com.mss.thread.service;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

public class OrderService {
    public void startOrderService() {
        System.out.println(PrintHelper.printThreadMark() + "do startOrderService()...");
        SleepHelper.sleep(5000);
        System.out.println(PrintHelper.printThreadMark() + "startOrderService() end.");
    }

    public String getOrderServiceResult() {
        System.out.println(PrintHelper.printThreadMark() + "do getOrderServiceResult()...");
        SleepHelper.sleep(5000);
        System.out.println(PrintHelper.printThreadMark() + "getOrderServiceResult() end.");
        return "OrderId: 18";
    }
}
