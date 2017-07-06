package com.example.administrator.myweather.MyAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myweather.R;
import com.example.administrator.myweather.bean.future;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */

public class futureListAdapter extends BaseAdapter {
    public List<future> futureList;
    public Context context;

    public static  class ViewHoder{
        public TextView lv_date;
        public TextView lv_weather;
        public TextView lv_temperature;
        public TextView lv_wind;
    }




    public futureListAdapter(List<future> futureList, Context context) {
        this.futureList = futureList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return futureList.size();
    }

    @Override
    public Object getItem(int position) {
        return futureList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHoder viewHoder;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.future_item,null);
            viewHoder = new ViewHoder();
            viewHoder.lv_date = (TextView) convertView.findViewById(R.id.lv_date);
            viewHoder.lv_temperature = (TextView) convertView.findViewById(R.id.lv_temperature);
            viewHoder.lv_weather = (TextView) convertView.findViewById(R.id.lv_weather);
            viewHoder.lv_wind = (TextView) convertView.findViewById(R.id.lv_wind);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder) convertView.getTag();
        }


        if (position == 0) {
            String week = "周"+futureList.get(position).getWeek().substring(2,3);
            viewHoder.lv_date.setText("今天 "+week);
        }else if (position == 1){
            String week = "周"+futureList.get(position).getWeek().substring(2,3);
            viewHoder.lv_date.setText("明天 "+week);
        }else if (position == 2){
            String week = "周"+futureList.get(position).getWeek().substring(2,3);
            viewHoder.lv_date.setText("后天 "+week);
        }else {
            String week = "周"+futureList.get(position).getWeek().substring(2,3);
            String ss = futureList.get(position).getDate().substring(4,8);//获取后几位月份与日期
            int m = Integer.parseInt(ss.substring(0,2));
            int d = Integer.parseInt(ss.substring(2,4));
            viewHoder.lv_date.setText(m+"-"+d+"   "+week);
        }

        viewHoder.lv_wind.setText(futureList.get(position).getWind());
        viewHoder.lv_weather.setText(futureList.get(position).getWeather());
        viewHoder.lv_temperature.setText(futureList.get(position).getTemperature());


        return convertView;
    }


}
