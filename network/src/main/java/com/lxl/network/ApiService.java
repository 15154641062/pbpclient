package com.lxl.network;

import com.lxl.network.beans.BaseResponse;
import com.lxl.network.beans.TencentBaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 请求方法
 */
public interface ApiService {
    //https://api.apiopen.top/musicDetails?id=604392760  接口完整路径
    /**
     * get请求方式
     * @Query
     * 形成单个查询参数, 将接口url中追加类似于"page=1"的字符串,形成提交给服务器端的参数,
     * 主要用于Get请求数据，用于拼接在拼接在url路径后面的查询参数，一个@Query相当于拼接一个参数
     */
    String HOST = "https://api.apiopen.top";        //接口地址
    @GET("/musicDetails")
    Observable<BaseResponse<List<TencentBaseResponse>>> getMeiZi(@Query("id") String id);

    /**
     * post请求方式
     */
    @FormUrlEncoded         //post请求必须要申明该注解
    @POST("musicDetails")   //方法名
    Observable<BaseResponse<List<TencentBaseResponse>>> getInfo(@Field("id") String data);//请求参数
}