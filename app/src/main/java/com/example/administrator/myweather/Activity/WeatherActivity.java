package com.example.administrator.myweather.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myweather.MyAdapter.futureListAdapter;
import com.example.administrator.myweather.R;
import com.example.administrator.myweather.Url.ConnentUrl;
import com.example.administrator.myweather.bean.future;
import com.example.administrator.myweather.bean.sk;
import com.example.administrator.myweather.bean.today;
import com.example.administrator.myweather.bean.weatherBean;
import com.example.administrator.myweather.utils.PrefUtil;
import com.example.administrator.myweather.utils.localUtils;
import com.example.administrator.myweather.view.RefreshListView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.jar.Manifest;

public class WeatherActivity extends Activity {


//    @ViewInject(R.id.con)
//    private TextView tv;

    @ViewInject(R.id.temp)
    private TextView temp;

    @ViewInject(R.id.time)
    private TextView time;

    @ViewInject(R.id.weather)
    private TextView weather;

    @ViewInject(R.id.temperature)
    private TextView temperature;

    @ViewInject(R.id.wind)
    private TextView wind;

    @ViewInject(R.id.wind_strength)
    private TextView wind_strength;

    @ViewInject(R.id.uv_index)
    private TextView uv_index;

    @ViewInject(R.id.dressing_index)
    private TextView dressing_index;

    @ViewInject(R.id.travel_index)
    private TextView travel_index;

    @ViewInject(R.id.city)
    TextView city;


    @ViewInject(R.id.future)
    private ListView future;

    @ViewInject(R.id.add)
    private ImageButton add;

    @ViewInject(R.id.local)
    private ImageButton local;



    private List<future> futureList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_activitye);
        x.view().inject(this);





        String cityName = PrefUtil.getString(this, "City", "");
        init(cityName);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this,SelectCityActivity.class);
                intent.putExtra("weather",true);
                startActivity(intent);
                finish();

            }
        });


        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23){

                    if (ActivityCompat.checkSelfPermission(WeatherActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions(WeatherActivity.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},100);

                    }else {
                        new localUtils().showLocation(WeatherActivity.this, new localUtils.CallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                Toast.makeText(WeatherActivity.this,"当前城市为"+response,Toast.LENGTH_LONG).show();
                                PrefUtil.setString(WeatherActivity.this,"City",response);
                                init(response);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(WeatherActivity.this,"定位失败！",Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                }else {
                    new localUtils().showLocation(WeatherActivity.this, new localUtils.CallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            PrefUtil.setString(WeatherActivity.this,"City",response);
                            Toast.makeText(WeatherActivity.this,"当前城市为"+response,Toast.LENGTH_LONG).show();
                            init(response);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(WeatherActivity.this,"定位失败！",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }




    public void init(String cityName){


        city.setText(cityName);
//        Toast.makeText(WeatherActivity.this,cityName,Toast.LENGTH_SHORT).show();


        final RequestParams params = new RequestParams(ConnentUrl.WEATHER_API);

        params.addQueryStringParameter("cityname", cityName);

        params.addQueryStringParameter("key", ConnentUrl.WEATHER_KEY);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result

                futureList = new ArrayList<com.example.administrator.myweather.bean.future>();

                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(result);

                String code = json.get("resultcode").getAsString();

                JsonObject results = json.get("result").getAsJsonObject();

                Gson gson = new Gson();
                today today = gson.fromJson(results.get("today").getAsJsonObject(), com.example.administrator.myweather.bean.today.class);
                sk sk = gson.fromJson(results.get("sk").getAsJsonObject(), com.example.administrator.myweather.bean.sk.class);

                JsonObject futureJson = results.get("future").getAsJsonObject();


                for (int i = 0; i < 7; i++) {  //获取七天的天气
                    Date date = new Date();
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    calendar.add(calendar.DATE, i);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
                    date = calendar.getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    String dateString = formatter.format(date);


                    future future = gson.fromJson(futureJson.get("day_" + dateString).getAsJsonObject(), com.example.administrator.myweather.bean.future.class);
                    futureList.add(future);
                }

                temp.setText(sk.getTemp());
                time.setText("今天"+sk.getTime()+"发布");
                weather.setText(today.getWeather());
                temperature.setText("今日气温："+today.getTemperature());
                wind.setText("当前风向："+sk.getWind_direction());
                wind_strength.setText("当前风力："+sk.getWind_strength());
                uv_index.setText("紫外线强度："+today.getUv_index());
                dressing_index.setText("穿衣指数："+today.getDressing_index());
                travel_index.setText("旅游指数："+today.getTravel_index());

                futureListAdapter futureListAdapter = new futureListAdapter(futureList,WeatherActivity.this);
                future.setAdapter(futureListAdapter);


            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(WeatherActivity.this,"连接失败",Toast.LENGTH_LONG).show();
            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 100:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    new localUtils().showLocation(WeatherActivity.this, new localUtils.CallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            Toast.makeText(WeatherActivity.this,"当前城市为"+response,Toast.LENGTH_LONG).show();
                            PrefUtil.setString(WeatherActivity.this,"City",response);
                            init(response);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(WeatherActivity.this,"定位失败！",Toast.LENGTH_LONG).show();
                        }
                    });

                }else {
                    Toast.makeText(WeatherActivity.this,"定位权限获取失败！",Toast.LENGTH_LONG).show();
                }

        }

    }
}
