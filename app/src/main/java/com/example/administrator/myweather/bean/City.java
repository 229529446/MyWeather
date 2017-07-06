package com.example.administrator.myweather.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 城市实体
 */

@Table(name = "City")
public class City {

    @Column(name = "cityName")
    private String cityName;

    @Column(name = "cityCode",isId = true)
    private String cityCode;

    @Column(name = "fromProvince")
    private String fromProvince;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getFromProvince() {
        return fromProvince;
    }

    public void setFromProvince(String fromProvince) {
        this.fromProvince = fromProvince;
    }

    public City() {
    }
}
