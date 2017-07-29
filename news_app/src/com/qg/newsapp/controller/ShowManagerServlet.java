package com.qg.newsapp.controller;

import com.google.gson.Gson;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.Manager;
import com.qg.newsapp.service.ManagerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        feedBack.setStatus(1);
        feedBack.setData(gson.toJson(managers));

        PrintWriter pw = new PrintWriter(response.getWriter());
        pw.write(gson.toJson(feedBack));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
