package com.lxl.network;

import android.util.Log;

import com.google.gson.Gson;
import com.lxl.network.utils.TencentUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkApi {
    private static final String BASE_URL="http://";
    private static final String TAG="NetworkApi";

    public static void getTencentNewsChannels(){
        Retrofit.Builder retrofitBuilder=new Retrofit.Builder();
        retrofitBuilder.baseUrl(BASE_URL);
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit =retrofitBuilder.build();
        NewsApiInterface tencentNewsService=retrofit.create(NewsApiInterface.class);
        String timeStr= TencentUtil.getTimeStr();
        Call newsChannels=tencentNewsService.getNewsChannels("source",TencentUtil.getAuthorization(timeStr),timeStr);
        newsChannels.enqueue(new Callback<NewsChannelsBean>() {
            @Override
            public void onResponse(Call<NewsChannelsBean> call, Response<NewsChannelsBean> response) {
                Log.e(TAG,new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
