package com.qg.newsapp.servlet;

import com.google.gson.Gson;
import com.qg.newsapp.dao.impl.NewDaoImpl;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.News;
import com.qg.newsapp.utils.LocalAddress;
import com.qg.newsapp.utils.StatusCode;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(name = "ReadServlet",urlPatterns = "/reader/read")
public class ReadServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        BufferedReader bufferedReader = new BufferedReader(request.getReader());
        String getString  ;
        StringBuilder getJson = new StringBuilder();
        //获得Json变成字符串
        while ((getString = bufferedReader.readLine())!=null)
            getJson.append(getString);
//        String getJson = getJsons.substring(1,getJsons.length());

        //用Gson来解析字符串
        Gson gson = new Gson();
        System.out.println(getJson+"或得到的Json");
//        JSONObject jsonObject = gson.fromJson(getJson.toString(),JSONObject.class);
//        //获得新闻的概要集合
//        News news = (News)jsonObject.get("startNews");
        News news = gson.fromJson(String.valueOf(getJson),News.class);

        List<News> newsList = new NewDaoImpl().GetNewsSummary(news);
        for (News newsl:newsList){
            StringBuilder newsBuilder = new StringBuilder(newsl.getNewsFace());
            String newsFace = newsBuilder.substring(newsBuilder.lastIndexOf("\\")+1);
            newsFace = "http://"+ LocalAddress.GetIPAddress()+":8080/news_face/"+newsFace;
            newsl.setNewsFace(newsFace);
        }

        FeedBack feedBack = new FeedBack();
        //判断返回的List是否是空的来决定响应的参数
        if (newsList == null)
            feedBack.setState(StatusCode.NEWS_DETIAL_IS_EMPTY.getStatusCode());
        else
            feedBack.setState(StatusCode.OK.getStatusCode());

        feedBack.setData(gson.toJson(newsList));

        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = new PrintWriter(response.getWriter());
        printWriter.write(gson.toJson(feedBack));
        System.out.println("成功");



    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

    }
}
