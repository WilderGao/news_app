package com.qg.newsapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.News;
import com.qg.newsapp.service.ManagerService;
import com.qg.newsapp.utils.StatusCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * 管理员查看自己的新闻，请求时传送一个管理员 id 过来，返回该管理员的所有新闻
 * Created by K Lin on 2017/7/30.
 */
@WebServlet(name = "ShowOwnNewsServlet",urlPatterns = {"/admin/showownnews"})
public class ShowOwnNewsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ManagerService managerService = new ManagerService();

        // 解决int型数据自动变为double型
        Gson gson = new GsonBuilder().
                registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                    if (src == src.longValue())
                        return new JsonPrimitive(src.longValue());
                    return new JsonPrimitive(src);
                }).create();
        // 读取JSON数据
        BufferedReader br = new BufferedReader(new
                InputStreamReader(request.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        //将 json 中的数据映射成一个 map 类型的值
        Map<String, Integer> map = gson.fromJson(String.valueOf(sb), new TypeToken<Map<String, Integer>>(){}.getType());
        int managerId = map.get("managerId");
        FeedBack feedBack = new FeedBack();
        List<News> ownNews = managerService.showManagerOwnNews(managerId);


        feedBack.setStatus(StatusCode.OK.getStatusCode());
        feedBack.setData(gson.toJson(ownNews));
        PrintWriter printWriter = new PrintWriter(response.getWriter());
        printWriter.write(gson.toJson(feedBack));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
