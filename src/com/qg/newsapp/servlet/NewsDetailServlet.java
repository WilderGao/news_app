package com.qg.newsapp.servlet;

import com.google.gson.Gson;
import com.mysql.jdbc.BufferRow;
import com.qg.newsapp.dao.impl.NewDaoImpl;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.News;
import com.qg.newsapp.utils.StatusCode;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "NewsDetailServlet",urlPatterns = "/reader/newsdetail")
public class NewsDetailServlet extends HttpServlet {
    NewDaoImpl newDao = new NewDaoImpl();
    News news = new News();
    FeedBack feedBack = new FeedBack();
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        bufferedReader = new BufferedReader(request.getReader());
        StringBuilder getFromJson = new StringBuilder();
        String context;
        while ( (context = bufferedReader.readLine())!=null)
            getFromJson.append(context);
        Gson gson = new Gson();
        JSONObject receiveJson = gson.fromJson(getFromJson.toString(),JSONObject.class);
        int newId = (int)receiveJson.get("newsId");
        //通过数据库查询和这个新闻Id有关的一切信息
        news = newDao.GetNewsDetail(newId);
        //返回参数和信息
        feedBack.setState(StatusCode.OK.getStatusCode());
        feedBack.setData(gson.toJson(news));
        String feedBackString = gson.toJson(feedBack);
        printWriter = response.getWriter();
        printWriter.write(feedBackString);

        //结束打印一下
        System.out.println("完成操作");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
