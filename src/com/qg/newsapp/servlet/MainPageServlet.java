package com.qg.newsapp.servlet;

import com.google.gson.Gson;
import com.qg.newsapp.dao.impl.NewDaoImpl;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.News;
import com.qg.newsapp.utils.StatusCode;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class MainPageServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        BufferedReader bufferedReader = new BufferedReader(request.getReader());
        String getString = "";
        StringBuilder getJson = new StringBuilder();
        //获得Json变成字符串
        while ((getString = bufferedReader.readLine())!=null)
            getJson.append(getString);

        //用Gson来解析字符串
        Gson gson = new Gson();
        JSONObject jsonObject = gson.fromJson(getJson.toString(),JSONObject.class);
        System.out.println("我得到的值为"+Integer.parseInt((String)jsonObject.get("startNews")));


        //获得新闻的概要集合
        int startNews = Integer.parseInt((String)jsonObject.get("startNews"));
        List<News> newsList = new NewDaoImpl().GetNewsSummary(startNews);


        FeedBack feedBack = new FeedBack();
        feedBack.setState(StatusCode.OK.getStatusCode());
        feedBack.setData(gson.toJson(newsList));

        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = new PrintWriter(response.getWriter());
        printWriter.write(gson.toJson(feedBack));



    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

    }
}
