package com.qg.newsapp.model;

/**
 * 返回的一般格式
 */
public class FeedBack {
    private int state;     //状态码
    private String data;        //返回信息的json数据

    public int getState() {
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
