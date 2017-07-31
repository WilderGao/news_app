package com.qg.newsapp.model;

/**
 * Created by K Lin on 2017/7/28.
 */
public class FeedBack {
    private int state;  // 返回状态码
    private String data;  // 返回 Json 数据

    public int getStatus() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
