package com.qg.newsapp.servlet;

import com.google.gson.Gson;
import com.qg.newsapp.dao.impl.NewDaoImpl;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.News;
import com.qg.newsapp.utils.StatusCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "FlashServlet",urlPatterns = "/reader/flashNews")
public class FlashServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter printWriter = new PrintWriter(response.getWriter());
        Gson gson = new Gson();
        NewDaoImpl newDao = new NewDaoImpl();
        //得到最新的十条信息的
        List<News> newsList = newDao.GetCurrentNews();
        String newsListString = gson.toJson(newsList);

        FeedBack feedBack = new FeedBack();
        feedBack.setState(StatusCode.OK.getStatusCode());
        feedBack.setData(newsListString);

        printWriter.write(gson.toJson(feedBack));
        System.out.println("获取了最新的十条信息");


    }
}
