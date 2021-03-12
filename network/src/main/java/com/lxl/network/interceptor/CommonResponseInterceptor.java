package com.lxl.network.interceptor;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

public class CommonResponseInterceptor {
    public static final String TAG = "ResponseInterceptor";

    public static HttpLoggingInterceptor LogInterceptor() {
        //日志拦截器,用于打印返回请求的结果
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(TAG, "log:" + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
