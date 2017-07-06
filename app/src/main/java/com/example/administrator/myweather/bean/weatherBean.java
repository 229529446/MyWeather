package com.example.administrator.myweather.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class weatherBean {

    private sk sk;
    private today today;
    private List<future> future;

    public com.example.administrator.myweather.bean.sk getSk() {
        return sk;
    }

    public void setSk(com.example.administrator.myweather.bean.sk sk) {
        this.sk = sk;
    }

    public com.example.administrator.myweather.bean.today getToday() {
        return today;
    }

    public void setToday(com.example.administrator.myweather.bean.today today) {
        this.today = today;
    }

    public List<com.example.administrator.myweather.bean.future> getFuture() {
        return future;
    }

    public void setFuture(List<com.example.administrator.myweather.bean.future> future) {
        this.future = future;
    }


    @Override
    public String toString() {
        return "weatherBean{" +
                "sk=" + sk +
                ", today=" + today +
                ", future=" + future +
                '}';
    }
}
