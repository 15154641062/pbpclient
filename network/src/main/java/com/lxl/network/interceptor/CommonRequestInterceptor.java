package com.lxl.network.interceptor;

import android.content.Intent;
import android.text.TextUtils;

import com.lxl.network.api.AuthenticateApiService;
import com.lxl.network.base.BaseObserver;
import com.lxl.network.base.BaseRequest;
import com.lxl.network.base.BaseResponse;
import com.lxl.pbpclient.activity.LoginActivity;
import com.lxl.pbpclient.util.ApplicationUtil;
import com.lxl.pbpclient.util.SharedPrefsUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import kotlin.jvm.Synchronized;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonRequestInterceptor implements Interceptor {

    private static final String OS = "android";


    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", OS);
        builder.addHeader("version", "1.0.0");
        builder.addHeader("contentType", "application/json;charset=UTF-8");

        // 从本地SharedPreference获取token
        String token = SharedPrefsUtil.getStrFromSharedPrefs("token");

        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization", token);
        }
        //判断token是否过期
        if (isTokenExpired(response)) {
            //同步请求方式，获取最新的Token
            builder.header("Authorization", updateTokenByRefreshToken());
        }
        return chain.proceed(builder.build());
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        if (response.code() == 401) {
            return true;
        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    @Synchronized
    private String updateTokenByRefreshToken() {

        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求

        BaseRequest.getService(AuthenticateApiService.class)
                .updateToken(SharedPrefsUtil.getStrFromSharedPrefs("refreshToken"))         // 从SharedPreference中获取refreshToken的值
                .compose(BaseRequest.applyScheduler(new BaseObserver<HashMap<String,String>>() {
                    @Override
                    protected void onSuccess(BaseResponse<HashMap<String,String>> response) {
                        if (response.getCode() == 0) {
                            SharedPrefsUtil.saveStrToSharedPrefs("token",response.getResults().get("token"));
                            SharedPrefsUtil.saveStrToSharedPrefs("refreshToken",response.getResults().get("refreshToken"));
                        }
                    }

                    @Override
                    protected void onFailed(Throwable e) {
                        // 跳转登录界面
                        Intent intent=new Intent(ApplicationUtil.getContext(), LoginActivity.class);
                        ApplicationUtil.getContext().startActivity(intent);
                    }
                }));
        return SharedPrefsUtil.getStrFromSharedPrefs("token");
    }
}
