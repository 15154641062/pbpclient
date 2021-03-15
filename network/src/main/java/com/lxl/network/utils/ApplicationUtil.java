package com.lxl.network.utils;

import android.app.Application;
import android.content.Context;

/**
 * 全局获取context
 */
public class ApplicationUtil extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
