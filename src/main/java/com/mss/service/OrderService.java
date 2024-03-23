package com.mss.service;

import com.mss.utils.SleepHelper;

public class OrderService {
    public void startOrderService() {
        System.out.println("OrderService start...");
        SleepHelper.sleep(5000);
        System.out.println("OrderService end...");
    }

    public String getOrderServiceResult() {
        System.out.println("OrderService start...");
        SleepHelper.sleep(5000);
        System.out.println("OrderService end...");
        return "OrderId: 18";
    }
}
