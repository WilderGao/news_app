package com.qg.newsapp.servlet;

import com.google.gson.Gson;
import com.mysql.jdbc.BufferRow;
import com.qg.newsapp.dao.impl.NewDaoImpl;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.News;
import com.qg.newsapp.model.ViceFile;
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

@WebServlet(name = "NewsDetailServlet",urlPatterns = "/reader/newsdetail")
public class NewsDetailServlet extends HttpServlet {
    FeedBack feedBack = new FeedBack();
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    StringBuilder getFromJson;
    protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        bufferedReader = new BufferedReader(request.getReader());
        printWriter = new PrintWriter(response.getWriter());
         getFromJson = new StringBuilder();
        String context ;
        while ( (context = bufferedReader.readLine())!=null) {
            getFromJson.append(context);
        }
        Gson gson = new Gson();
        JSONObject receiveJson = gson.fromJson(getFromJson.toString(),JSONObject.class);
        Double newId = (Double) receiveJson.get("newsId");
        System.out.println(newId+"新闻Id");
        //通过数据库查询和这个新闻Id有关的一切信息
        News news = new NewDaoImpl().GetNewsDetail(newId.intValue());

        //修改新闻附件和封面的路径让客户端可以下载
        if (news.getFileList().size()!=0) {
            List<ViceFile> viceFileList = news.getFileList();
            for (ViceFile viceFile : viceFileList) {
                StringBuilder filePath = new StringBuilder(viceFile.getFilePath());
                String vicePath = filePath.substring(filePath.lastIndexOf("\\") + 1);
                vicePath = "http://" + LocalAddress.GetIPAddress() + ":8080/saved/news_id：" + news.getNewsId() + "/" + vicePath;
                viceFile.setFilePath(vicePath);
            }
        }
        //修改封面路径
        System.out.println(news.getNewsFace()+"封面路径");
        StringBuilder fileFace = new StringBuilder(news.getNewsFace());
        System.out.println("得到的StringBuilder"+fileFace);
        String fileFaceChangePath = fileFace.substring(fileFace.lastIndexOf("\\")+1);
        fileFaceChangePath = "http://"+LocalAddress.GetIPAddress()+":8080/news_face"+fileFaceChangePath;
        news.setNewsFace(fileFaceChangePath);


        //返回参数和信息
        if (news == null)
            feedBack.setState(StatusCode.NEWS_DETIAL_IS_EMPTY.getStatusCode());
        else
            feedBack.setState(StatusCode.OK.getStatusCode());
            feedBack.setData(gson.toJson(news));
            String feedBackString = gson.toJson(feedBack);
            response.setCharacterEncoding("utf-8");
            printWriter.write(feedBackString);


        //结束打印一下
        System.out.println("完成操作");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
