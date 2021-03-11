package com.lxl.network.beans;

import com.lxl.network.ApiService;
import com.lxl.network.NetworkApi;
import com.lxl.network.utils.InterceptorUtil;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRequest {
    //初始化OkHttp,绑定拦截器事件
    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        return okHttpClient.connectTimeout(20, TimeUnit.SECONDS)                     //设置请求超时时间
                .readTimeout(20, TimeUnit.SECONDS)                        //设置读取数据超时时间
                .writeTimeout(20, TimeUnit.SECONDS)                       //设置写入数据超时时间
                .addInterceptor(InterceptorUtil.LogInterceptor())                 //绑定日志拦截器
                .addNetworkInterceptor(InterceptorUtil.HeaderInterceptor())       //绑定header拦截器
                .build();
    }

    private static HashMap<String,Retrofit> retrofitMap = new HashMap<>();

    public static Retrofit getRetrofit(Class service) {
        if(retrofitMap.get(NetworkApi.getBaseUrl() + service.getName()) != null){
            return retrofitMap.get(NetworkApi.getBaseUrl() + service.getName());
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkApi.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
        //如果不存在，就将Retrofit添加到map集合
        retrofitMap.put(NetworkApi.getBaseUrl() + service.getName(),retrofit);
        return retrofit;
    }

    //统一的接口调用
    public static <T> T getService(Class<T> service){
        return getRetrofit(service).create(service);
    }

    //线程切换的封装
    public static <T> ObservableTransformer<T, T> applyScheduler(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                observable.subscribe(observer);
                return observable;
            }
        };
    }

}
