package com.lxl.network.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TencentBaseResponse {
    @SerializedName("showApiResCode")
    @Expose
    public Integer showApiResCode;
    @SerializedName("showApiResError")
    @Expose
    public String showApiResError;
}
