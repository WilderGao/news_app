package com.qg.newsapp.servlet;

import com.google.gson.Gson;
import com.qg.newsapp.dao.impl.NewDaoImpl;
import com.qg.newsapp.dao.impl.VisitorDaolmpl;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.News;
import com.qg.newsapp.utils.LocalAddress;
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
import java.util.List;

@WebServlet(name = "FlashServlet",urlPatterns = "/reader/flashnews")
public class FlashServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader bufferedReader = new BufferedReader(request.getReader());
        String context ;
        StringBuilder sb = new StringBuilder();
        while ((context = bufferedReader.readLine())!=null)
            sb.append(context);
        System.out.println("sb的内容是"+sb.toString());
        Gson gson = new Gson();
        if (!sb.toString().equals("")){
            JSONObject jsonObject = gson.fromJson(sb.toString(),JSONObject.class);
            String visitorUUID = (String) jsonObject.get("visitorUUID");
            new VisitorDaolmpl().InsertVisitor(visitorUUID);
        }

        PrintWriter printWriter = new PrintWriter(response.getWriter());
        NewDaoImpl newDao = new NewDaoImpl();
        //得到最新的十条信息的
        List<News> newsList = newDao.GetCurrentNews();
        System.out.println("一共发送的信息条数为"+newsList.size());
        for (News news:newsList){
            StringBuilder newsBuilder = new StringBuilder(news.getNewsFace());
            String newsFace = newsBuilder.substring(newsBuilder.lastIndexOf("\\")+1);
            System.out.println(newsFace+"截取后的路径");
            newsFace = "http://"+LocalAddress.GetIPAddress()+":8080/news_face/"+newsFace;
            news.setNewsFace(newsFace);
        }
        String newsListString = gson.toJson(newsList);
        response.setCharacterEncoding("utf-8");

        FeedBack feedBack = new FeedBack();
        feedBack.setState(StatusCode.OK.getStatusCode());
        feedBack.setData(newsListString);

        printWriter.write(gson.toJson(feedBack));
        System.out.println("获取了最新的十条信息");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
