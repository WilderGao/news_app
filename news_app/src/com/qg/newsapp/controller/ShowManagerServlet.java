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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 展示所有管理员账户（状态为正常的账户）
 * Created by K Lin on 2017/7/28.
 */
@WebServlet(name = "ShowManagerServlet",urlPatterns={"/admin/showmanager"})
public class ShowManagerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ManagerService managerService = new ManagerService();
        List<Manager> managers =  managerService.showManager();
        Gson gson = new Gson();

        FeedBack feedBack = new FeedBack();

        feedBack.setState(StatusCode.OK.getStatusCode());
        // 将 Manager 类的 List 集转为 json 字符串存入 feedBack 中的Data属性
        feedBack.setData(gson.toJson(managers));


        PrintWriter pw = new PrintWriter(response.getWriter());
        // 将 feedBack 类转化为 json 字符串发送回移动端
        pw.write(gson.toJson(feedBack));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
