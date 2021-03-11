package com.lxl.network.utils;

import android.text.TextUtils;

import com.lxl.network.ApiService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Response response = chain.proceed(request);
        Request.Builder requestBuilder = original.newBuilder();
        String access_token = (String) SPHelper.get(AppContext.get(),"access_token","");
        if(!TextUtils.isEmpty(access_token)){
            requestBuilder.addHeader("Authentication", access_token);
        }

        if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
            //同步请求方式，获取最新的Token
           access_token = getNewToken();
             requestBuilder.header("Authentication", access_token);
        }
          Request request = requestBuilder.build();
       return chain.proceed(request);
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
    private String getNewToken() throws IOException {
        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
        String refreshToken = "XXXXXXXXXXXXXXXXXXX";

        HttpHelper httpHelper=  new HttpHelper(application);
        ApiService service = httpHelper.getApi(ApiService.class);
        Call<String> call = service.refreshToken(refreshToken);
       //使用retrofit的同步方式
        String newToken = call.execute().body();
        SPHelper.put(AppContext.get(),"access_token",newToken)//存本地
        return newToken;
    }
}
