package com.qg.newsapp.controller;

import com.google.gson.Gson;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.service.ManagerService;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * 接收超级管理员拒绝审批请求，被拒绝审批的账户需要在数据库中进行删除操作
 * Created by K Lin on 2017/7/29.
 */
@WebServlet(name = "RefuseManagerServlet",urlPatterns={"/admin/approvalrefuse"})
public class RefuseManagerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        ManagerService managerService = new ManagerService();
        FeedBack feedBack = new FeedBack();
        String line1 = "";
        String line2 = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        while((line1=br.readLine())!=null){
            line2 += line1;
        }
        JSONObject jsonObject = gson.fromJson(line2,JSONObject.class);

        Double manageridd=(Double) jsonObject.get("managerId");
        int managerid = manageridd.intValue();


        if(managerService.refuseManager(managerid)){
            feedBack.setStatus(1);
            feedBack.setStatusInfo("成功拒绝审批");
            feedBack.setData(null);
        }else{
            feedBack.setStatus(5000);
            feedBack.setStatusInfo("拒绝审批操作失败");
            feedBack.setData(null);
        }

        PrintWriter pw = new PrintWriter(response.getWriter());
        pw.write(gson.toJson(feedBack));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
