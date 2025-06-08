package com.mss.thread.service;

import com.mss.utils.PrintHelper;
import com.mss.utils.SleepHelper;

public class UserService {
    public void startUserService() {
        System.out.println(PrintHelper.printThreadMark() + "do startUserService()...");
        SleepHelper.sleep(3000);
        System.out.println(PrintHelper.printThreadMark() + "startUserService() end.");
    }

    public String getUserServiceResult() {
        System.out.println(PrintHelper.printThreadMark() + "do getUserServiceResult()...");
        SleepHelper.sleep(3000);
        System.out.println(PrintHelper.printThreadMark() + "getUserServiceResult() end.");
        return "Username: mss";
    }
}
