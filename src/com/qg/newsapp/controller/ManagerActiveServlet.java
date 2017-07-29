package com.qg.newsapp.controller;

import com.google.gson.Gson;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.service.ManagerService;
import com.qg.newsapp.utils.StatusCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ManagerActiveServlet", urlPatterns = "/admin/verifyaccount")
public class ManagerActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code"); // 用户激活码
        ManagerService managerService = new ManagerService();
        Gson gson = new Gson();
        FeedBack feedBack = new FeedBack();

        if (managerService.active(code)) {
            feedBack.setStatus(StatusCode.OK.getStatusCode());
        } else {
            feedBack.setStatus(StatusCode.Server_Error.getStatusCode());
        }
        response.getWriter().write(gson.toJson(feedBack));

    }
}
