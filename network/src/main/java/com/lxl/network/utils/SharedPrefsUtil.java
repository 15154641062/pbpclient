package com.lxl.network.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsUtil {
    private static final String SHARED_PREFS_NAME="sp_config";

    /**
     * 保存字符串到SharedPreferences
     * @param key
     * @param val
     */
    public static void saveStrToSharedPrefs(String key,String val){
        SharedPreferences sharedPreferences = ApplicationUtil.getContext().getSharedPreferences(SHARED_PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key, val);
        editor.commit();
    }

    /**
     * 从SharedPreferences获取字符串
     * @param key
     * @return
     */
    public static String getStrFromSharedPrefs(String key){
        SharedPreferences sharedPreferences = ApplicationUtil.getContext().getSharedPreferences(SHARED_PREFS_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}
