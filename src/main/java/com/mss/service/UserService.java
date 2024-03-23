package com.mss.service;

import com.mss.utils.SleepHelper;
import com.mss.utils.TimeUtils;

public class UserService {
    public void startUserService() {
        System.out.println(TimeUtils.getCurrentFormattedTime() + " UserService start...");
        SleepHelper.sleep(3000);
        System.out.println(TimeUtils.getCurrentFormattedTime() + " UserService end...");
    }

    public String getUserServiceResult() {
        System.out.println(TimeUtils.getCurrentFormattedTime() + " UserService start...");
        SleepHelper.sleep(3000);
        System.out.println(TimeUtils.getCurrentFormattedTime() + " UserService end...");
        return "Username: mss";
    }
}
