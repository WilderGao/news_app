package com.qg.newsapp.model;

/**
 * Created by K Lin on 2017/7/28.
 */
public class FeedBack {
    private int status;  // 返回状态码
    private String data;  // 返回 Json 数据

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
