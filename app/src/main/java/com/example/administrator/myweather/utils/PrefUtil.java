package com.example.administrator.myweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/7/3.
 */
public class PrefUtil {

    public  static final  String PREF_NAME = "config";


    public static String getString(Context ctx, String key, String defaultValue){
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sp.getString(key,defaultValue);
    }

    public static void setString(Context ctx,String key, String value){
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }




}
