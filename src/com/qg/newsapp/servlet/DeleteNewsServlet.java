package com.qg.newsapp.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.qg.newsapp.dao.impl.NewDaoImpl;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.News;
import com.qg.newsapp.model.ViceFile;
import com.qg.newsapp.utils.DeleteFile;

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

@WebServlet(name = "DeleteNewsServlet",urlPatterns = "/admin/deletenews")
public class DeleteNewsServlet extends HttpServlet {
    News news;
    BufferedReader bufferedReader ;
    PrintWriter printWriter;
    NewDaoImpl newDao = new NewDaoImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new GsonBuilder().
                registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                    if (src == src.longValue())
                        return new JsonPrimitive(src.longValue());
                    return new JsonPrimitive(src);
                }).create();

        // 读取JSON数据
         bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        Map<String, Integer> map = gson.fromJson(String.valueOf(sb), new TypeToken<Map<String, Integer>>(){}.getType());
        //得到要删除的新闻Id
        int newsId = map.get("newsId");
        //得到要删除新闻的Id，后面用来找到附件路径和封面来删除
        news = newDao.GetNewsDetail(newsId);
        //得到附件的信息
        List<ViceFile> fileList = news.getFileList();
        for (ViceFile viceFile :fileList){
            //找到附件的路径并且将文件删除
            DeleteFile.delete(viceFile.getFilePath());
        }
        //删除封面
        DeleteFile.delete(news.getNewsFace());
        //最后删除数据库中的数据,得到数据库返回的状态码
        int status = newDao.DeleteNews(newsId);

        //响应
        FeedBack feedBack = new FeedBack();
        feedBack.setState(status);
        printWriter = new PrintWriter(response.getWriter());
        printWriter.write(gson.toJson(feedBack));

        //发送成功
        System.out.println("日志====="+newsId+"被管理员"+news.getNewsAuthor()+"删除，他的Id为"+news.getManagerId());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
