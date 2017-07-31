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
 * 展示未审批的管理员的列表，在每个管理员后面有"通过"和"拒绝"两个链接提供管理员操作
 * Created by K Lin on 2017/7/29.
 */
@WebServlet(name = "ShowUnApprovalManagerServlet",urlPatterns={"/admin/showmanagerapproval"})
public class ShowUnApprovalManagerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ManagerService managerService = new ManagerService();
        List<Manager> managers =  managerService.showUnApprovalManager();
        Gson gson = new Gson();

        FeedBack feedBack = new FeedBack();

        feedBack.setState(StatusCode.OK.getStatusCode());
        // 将 managers 以 json 的格式传入 feedback 中
        feedBack.setData(gson.toJson(managers));

        PrintWriter pw = new PrintWriter(response.getWriter());
        pw.write(gson.toJson(feedBack));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
