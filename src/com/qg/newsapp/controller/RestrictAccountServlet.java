package com.qg.newsapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.qg.newsapp.dao.impl.ManagerDaoImpl;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.Manager;
import com.qg.newsapp.utils.MailUtil;
import com.qg.newsapp.utils.ManagerStatus;
import com.qg.newsapp.utils.StatusCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * 将管理员封号
 */
@WebServlet(name = "RestrictAccountServlet", urlPatterns = "/admin/restrictaccount")
public class RestrictAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 解决int型数据自动变为double型
        Gson gson = new GsonBuilder().
                registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                    if (src == src.longValue())
                        return new JsonPrimitive(src.longValue());
                    return new JsonPrimitive(src);
                }).create();
        ManagerDaoImpl dao = new ManagerDaoImpl();
        MailUtil mailUtil = new MailUtil();
        FeedBack feedBack = new FeedBack();

        // 读取JSON数据
        BufferedReader br = new BufferedReader(new
                InputStreamReader(request.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        Map<String, Integer> map = gson.fromJson(String.valueOf(sb), new TypeToken<Map<String, Integer>>(){}.getType());

        if (dao.updateManagerStatus(map.get("managerId"), ManagerStatus.BE_SEAL.getName())) {
            // 如果操作成功就发送邮件
            Manager manager = dao.getManagerById(map.get("managerId"));
            try {
                mailUtil.sendMail(manager.getManagerAccount(), "被封号通知",
                        "<h1>你已经被管理员封号，如有疑问请尽快联系超级管理员</h1>",
                        "text/html;charset=UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            feedBack.setStatus(StatusCode.OK.getStatusCode());
        } else {
            feedBack.setStatus(StatusCode.Server_Error.getStatusCode());
        }
        response.getWriter().write(gson.toJson(feedBack));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
