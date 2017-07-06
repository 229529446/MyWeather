package com.example.administrator.myweather.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.myweather.Application.MyApplication;
import com.example.administrator.myweather.MyAdapter.CityListAdapter;
import com.example.administrator.myweather.MyAdapter.ProvinceListAdapter;
import com.example.administrator.myweather.R;
import com.example.administrator.myweather.bean.City;
import com.example.administrator.myweather.bean.Province;
import com.example.administrator.myweather.utils.PrefUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class SelectCityActivity extends Activity  {


    @ViewInject(R.id.province_list)
    private ListView provinceList;
    @ViewInject(R.id.city_list)
    private ListView cityList;

    private List<City> cityListData;

    private boolean isFromWeatherActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFromWeatherActivity = getIntent().getBooleanExtra("weather", false);


        // 如果country已选择且本Activity不是从天气界面启动而来的，则直接跳转到WeatherActivity

        if (!TextUtils.isEmpty(PrefUtil.getString(this,"City", "")) && !isFromWeatherActivity) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_city);
        final DbManager db = x.getDb(((MyApplication) getApplication()).getDao());




        x.view().inject(this);


        List<Province> ProvinceList;

        try {
            ProvinceList = db.selector(Province.class).findAll();
            ProvinceListAdapter provinceListAdapter = new ProvinceListAdapter(ProvinceList, this);
            provinceList.setAdapter(provinceListAdapter);
        } catch (DbException e) {
            e.printStackTrace();
        }







        provinceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityListAdapter cityListAdapter = null;
                String code = 1+position+"";
                if (code.length() == 1){
                    code = "0"+code;
                }

                try {
                    cityListData = db.selector(City.class).where("fromProvince", "=", code).findAll();
                    cityListAdapter = new CityListAdapter(cityListData, SelectCityActivity.this);
//                    Log.i("ItemClick", "onItemClick: "+position);
                } catch (DbException e) {
                    e.printStackTrace();
                }

                cityList.setAdapter(cityListAdapter);

            }
        });


        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(SelectCityActivity.this,cityListData.get(position).getCityName(),Toast.LENGTH_SHORT).show();


                PrefUtil.setString(SelectCityActivity.this,"City",cityListData.get(position).getCityName());
                Intent intent = new Intent(SelectCityActivity.this,WeatherActivity.class);
                SelectCityActivity.this.startActivity(intent);
                SelectCityActivity.this.finish();


            }
        });


    }
}
