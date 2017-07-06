package com.example.administrator.myweather.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 省实体
 */

@Table(name = "Province")
public class Province {

    @Column(name = "provinceName")
    private String provinceName;

    @Column(name = "provinceCode",isId = true)
    private String provinceCode;


    public Province() {

    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
