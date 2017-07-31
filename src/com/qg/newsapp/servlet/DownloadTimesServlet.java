package com.qg.newsapp.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.qg.newsapp.dao.impl.VisitorDownloadDaolmpl;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.ViceFile;
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

@WebServlet(name = "DownloadTimesServlet",urlPatterns = "/admin/downloadtime")
public class DownloadTimesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader bufferedReader;
        PrintWriter printWriter;
        VisitorDownloadDaolmpl visitorDownloadDaolmpl = new VisitorDownloadDaolmpl();

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
        //获得管理员的Id
        int managerId = map.get("managerId");
        //获得和这个Id有关的所有附件的集合
        List<ViceFile> viceFileList = visitorDownloadDaolmpl.GetViceFileList(managerId);

        //返回响应到安卓端
        FeedBack feedBack = new FeedBack();
        feedBack.setState(StatusCode.OK.getStatusCode());
        feedBack.setData(gson.toJson(viceFileList));

        printWriter = new PrintWriter(response.getWriter());
        printWriter.write(gson.toJson(feedBack));
        //返回成功
        System.out.println("返回成功");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
