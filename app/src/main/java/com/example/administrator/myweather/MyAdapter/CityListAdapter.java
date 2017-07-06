package com.example.administrator.myweather.MyAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myweather.R;
import com.example.administrator.myweather.bean.City;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */
public class CityListAdapter extends BaseAdapter{

    List<City> cityList;
    private Context context;

    public CityListAdapter(List<City> cityList,Context context) {
        this.cityList = cityList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.city_list_item,null);
            viewHoder = new ViewHoder();
            viewHoder.CityName = (TextView) convertView.findViewById(R.id.city_name);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder) convertView.getTag();
        }

        viewHoder.CityName.setText(cityList.get(position).getCityName());
        return convertView;
    }



    static  class ViewHoder{
        public TextView CityName;
    }
}
