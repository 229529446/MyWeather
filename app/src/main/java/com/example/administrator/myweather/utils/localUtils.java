package com.example.administrator.myweather.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.myweather.Url.ConnentUrl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */

public class localUtils {



    public void showLocation(Context context, final CallbackListener listener){

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        List<String> providerList = locationManager.getProviders(true);
        String provider;

        if (providerList.contains(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
        }else if (providerList.contains(LocationManager.NETWORK_PROVIDER)){
            provider = LocationManager.NETWORK_PROVIDER;
        }else{
            Toast.makeText(context,"请打开GPS定位！并授权软件",Toast.LENGTH_LONG).show();
            listener.onError(new Throwable("请打开GPS定位"));
            return;
        }



        Location location = locationManager.getLastKnownLocation(provider);
        Log.i("location", "showLocation: "+location.getLatitude()+","+location.getLongitude());

        //http://api.map.baidu.com/geocoder/v2/?ak=BNbbGo7ffcfdubiIIu64Sl9etWD0NloO&callback=renderReverse&location=23.064289999999996,113.57248&output=json&pois=0

        final RequestParams params = new RequestParams(ConnentUrl.LOCATION);

        params.addQueryStringParameter("ak", "BNbbGo7ffcfdubiIIu64Sl9etWD0NloO");


        params.addQueryStringParameter("location", location.getLatitude()+","+location.getLongitude());

        params.addQueryStringParameter("output","json");

        params.addQueryStringParameter("pois","0");





        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("location", "onSuccess: "+result);
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(result);
                JsonObject res = jsonObject.getAsJsonObject("result");
                JsonObject addressComponent = res.getAsJsonObject("addressComponent");
                String address = addressComponent.get("city").getAsString();
                address = address.substring(0,address.length()-1);
                listener.onFinish(address);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listener.onError(ex);
                Log.i("location", "onError: ++++++++++++++++++++++"+ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
            }
        });





    }

    public interface CallbackListener{
        void onFinish(String response);
        void onError(Throwable  e);
    }

}
