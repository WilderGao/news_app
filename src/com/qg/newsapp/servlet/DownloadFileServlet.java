package com.qg.newsapp.servlet;

import com.google.gson.Gson;
import com.mysql.jdbc.BufferRow;
import com.qg.newsapp.dao.impl.VisitorDownloadDaolmpl;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.Visitor;
import com.qg.newsapp.model.VisitorDownload;
import com.qg.newsapp.utils.StatusCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "DownloadFileServlet",urlPatterns = "/reader/downloadfile")
public class DownloadFileServlet extends HttpServlet {
    BufferedReader bufferedReader ;
    PrintWriter printWriter;
    Gson gson  = new Gson();
    VisitorDownloadDaolmpl visitorDownloadDaolmpl = new VisitorDownloadDaolmpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        bufferedReader = new BufferedReader(request.getReader());
        String context;
        StringBuilder getFromJson = new StringBuilder();
        while ((context = bufferedReader.readLine())!=null){
            getFromJson.append(context);
        }
        VisitorDownload visitorDownload = gson.fromJson(String.valueOf(getFromJson), VisitorDownload.class);
       int status =  visitorDownloadDaolmpl.InsertVisitorDownLoad(visitorDownload);
       response.setCharacterEncoding("utf-8");
       //响应参数
        FeedBack feedBack = new FeedBack();
        feedBack.setState(status);
        String feedBackString = gson.toJson(feedBack);
        printWriter = new PrintWriter(response.getWriter());
        printWriter.write(feedBackString);
        System.out.println("用户名"+visitorDownload.getDownloader()+"下载了文件");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
