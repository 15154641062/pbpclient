package com.lxl.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface NewsApiInterface {
    @GET("release/channel")
    Call<NewsChannelsBean> getNewsChannels(@Header("Source") String source,
                                           @Header("Authorization") String authorization,
                                           @Header("Date") String date);
}
