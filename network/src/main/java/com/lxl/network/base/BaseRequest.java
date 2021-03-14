package com.lxl.network.base;

import com.lxl.network.errorhandler.ExceptionHandle;
import com.lxl.network.errorhandler.HttpErrorHandler;
import com.lxl.network.interceptor.CommonRequestInterceptor;
import com.lxl.network.interceptor.CommonResponseInterceptor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRequest {
    private static final String BASE_URL = "http://";

    private static HashMap<String, Retrofit> retrofitMap = new HashMap<>();

    // 初始化OkHttp,绑定拦截器事件
    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        return okHttpClient.connectTimeout(20, TimeUnit.SECONDS)           // 设置请求超时时间
                .readTimeout(20, TimeUnit.SECONDS)                         // 设置读取数据超时时间
                .writeTimeout(20, TimeUnit.SECONDS)                        // 设置写入数据超时时间
                .addInterceptor(CommonResponseInterceptor.LogInterceptor())          // 绑定日志拦截器
                .addNetworkInterceptor(new CommonRequestInterceptor())               // 绑定header拦截器
                .addNetworkInterceptor(new CommonResponseInterceptor())               // 绑定response拦截器
                .build();
    }


    public static Retrofit getRetrofit(Class service) {
        if (retrofitMap.get(BASE_URL + service.getName()) != null) {
            return retrofitMap.get(BASE_URL + service.getName());
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
        // 如果不存在，就将Retrofit添加到map集合
        retrofitMap.put(BASE_URL + service.getName(), retrofit);
        return retrofit;
    }

    // 统一的接口调用
    public static <T> T getService(Class<T> service) {
        return getRetrofit(service).create(service);
    }

    // 线程切换的封装
    public static <T> ObservableTransformer<T, T> applyScheduler(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).map(BaseRequest.<T>getAppErrorHandler()).onErrorResumeNext(new HttpErrorHandler<T>());
                ;
                observable.subscribe(observer);
                return observable;
            }
        };
    }

    // app错误处理的封装
    public static <T> Function<T, T> getAppErrorHandler() {
        return (response) -> {
            //response中code码不会0 出现错误
            if (response instanceof BaseResponse && ((BaseResponse) response).getCode() != 0) {
                ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
                exception.code = ((BaseResponse) response).getCode();
                exception.message = ((BaseResponse) response).getMessage() != null ? ((BaseResponse) response).getMessage() : "";
                throw exception;
            }
            return response;
        };
    }
}
