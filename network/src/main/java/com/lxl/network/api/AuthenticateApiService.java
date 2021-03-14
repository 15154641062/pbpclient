package com.lxl.network.api;

import com.lxl.network.NewsChannelsBean;
import com.lxl.network.base.BaseResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthenticateApiService {

    /**
     * 根据refreshToken刷新token
     * @return
     */
    @FormUrlEncoded        //post请求必须要申明该注解
    @POST("updateToken")   //方法名
    Observable<BaseResponse<HashMap<String,String>>> updateToken(@Field("refreshToken") String refreshToken);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<BaseResponse<String>> toLogin(@Field("username") String username,@Field("password") String password);
}
