package com.lxl.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsChannelsBean {
    @SerializedName("showApiResBody")
    @Expose
    public ShowApiResBody showApiResBody;

    public class ChannelList{
        @SerializedName("channelId")
        @Expose
        public String channelId;
        @SerializedName("name")
        @Expose
        public String name;
    }

    public class ShowApiResBody{
        @SerializedName("totalNum")
        @Expose
        public Integer totalNum;
        @SerializedName("retCode")
        @Expose
        public Integer retCode;
        @SerializedName("channelList")
        @Expose
        public List<ChannelList> channelList=null;
    }

}
