package com.qg.newsapp.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.qg.newsapp.dao.impl.VisitorDownloadDaolmpl;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.VisitorDownload;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DownloadDetailServlet",urlPatterns = "/admin/downloaddetail")
public class DownloadDetailServlet extends HttpServlet {
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    VisitorDownloadDaolmpl visitorDownloadDaolmpl = new VisitorDownloadDaolmpl();
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
        int filesId = map.get("filesId");
        //将这个附件的下载记录打包成一个List集合发送回来
        List<VisitorDownload> visitorDownloadList = visitorDownloadDaolmpl.VicefileDownloadDetail(filesId);
        //发送回安卓端
        FeedBack feedBack = new FeedBack();

        feedBack.setState(StatusCode.OK.getStatusCode());

        feedBack.setData(gson.toJson(visitorDownloadList));
        //发送回去安卓端
        printWriter = new PrintWriter(response.getWriter());
        printWriter.write(gson.toJson(feedBack));

        //测试打印一下
        System.out.println("发回去成功！！");





    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
