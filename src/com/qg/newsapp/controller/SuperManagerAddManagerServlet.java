package com.qg.newsapp.controller;

import com.google.gson.Gson;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.Manager;
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

/**
 * 超级管理员添加管理员
 */
@WebServlet(name = "SuperManagerAddManagerServlet", urlPatterns = "/admin/addmanager")
public class SuperManagerAddManagerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Manager manager;
        Gson gson = new Gson();
        ManagerService managerService = new ManagerService();
        FeedBack feedBack = new FeedBack();

        // 读取JSON数据
        BufferedReader br = new BufferedReader(new
                InputStreamReader(request.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        manager = gson.fromJson(String.valueOf(sb), Manager.class); // 把JSON数据转换为一个Manager对象
        // 判断用户名是否已经存在
        if (managerService.emailIsExist(manager.getManagerAccount())) {
            feedBack.setStatus(StatusCode.EMAIL_EXIST.getStatusCode());
            response.getWriter().write(gson.toJson(feedBack));
            return;
        }
        // 判断用户名格式是否正确
        if (!managerService.isRigthEmail(manager.getManagerAccount())) {
            feedBack.setStatus(StatusCode.EMAIL_FORMAT_IS_ERROR.getStatusCode());
            response.getWriter().write(gson.toJson(feedBack));
            return;
        }
        // 进行添加操作
        if (managerService.addManager(manager)) {
            feedBack.setStatus(StatusCode.OK.getStatusCode()); // 正常添加
        } else {
            feedBack.setStatus(StatusCode.Server_Error.getStatusCode()); // 未知错误
        }
        response.getWriter().write(gson.toJson(feedBack));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
