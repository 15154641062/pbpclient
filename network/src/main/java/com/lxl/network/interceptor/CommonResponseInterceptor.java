package com.lxl.network.interceptor;

import android.util.Log;

import com.lxl.pbpclient.util.SharedPrefsUtil;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class CommonResponseInterceptor implements Interceptor {
    public static final String TAG = "ResponseInterceptor";

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        //执行请求返回响应
        Response response = chain.proceed(chain.request());

        if(!StringUtils.isEmpty(response.header("Authorization"))){
            SharedPrefsUtil.saveStrToSharedPrefs("token",response.header("Authorization"));
        }
        if(!StringUtils.isEmpty(response.header("Refresh-Token"))){
            SharedPrefsUtil.saveStrToSharedPrefs("refreshToken",response.header("Refresh-Token"));
        }
        return response;
    }

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
