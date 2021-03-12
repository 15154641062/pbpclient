package com.lxl.network.interceptor;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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

        String token = " ";
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization", token);
        }
        //判断token是否过期
        if (isTokenExpired(response)) {
            //同步请求方式，获取最新的Token
            builder.header("Authorization", refreshToken());
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
    private String refreshToken() throws IOException {
        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
        String refreshToken = "XXXXXXXXXXXXXXXXXXX";

        //待解决
        /* HttpHelper httpHelper=  new HttpHelper(application);
        ApiService service = httpHelper.getApi(ApiService.class);
        Call<String> call = service.refreshToken(refreshToken);
        //使用retrofit的同步方式
        String newToken = call.execute().body();*/

        /**
         * todo token本地存储
         */
        String newToken = " ";
        return newToken;
    }
}
