package com.example.administrator.myweather.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.myweather.Application.MyApplication;
import com.example.administrator.myweather.Url.ConnentUrl;
import com.example.administrator.myweather.bean.City;
import com.example.administrator.myweather.bean.Province;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */
public class Utility {

    // 保存服务器返回的省级数据
    public static boolean saveProvincesResponse(Context context, String response) throws DbException {

        MyApplication myApplication = (MyApplication) context.getApplicationContext();
        DbManager db = x.getDb( myApplication.getDao());

        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");

            if (allProvinces != null && allProvinces.length > 0) {
                Province province;
                List<Province> provinceList = new ArrayList<Province>();
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    provinceList.add(province);
                    db.save(province);
                }

                return true;
            }
        }
        return false;
    }







    // 保存服务器返回的市级数据
    public static boolean saveCitiesResponse(Context context, String response, String provinceId) throws DbException {
        MyApplication myApplication = (MyApplication) context.getApplicationContext();
        DbManager db = x.getDb( myApplication.getDao());
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                City city;
                List<City> cityList = new ArrayList<City>();
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setFromProvince(array[0].substring(0,2));
                    cityList.add(city);
                }
                db.save(cityList);
                return true;
            }
        }
        return false;
    }




}
