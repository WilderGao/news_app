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
 * Created by K Lin on 2017/7/29.
 */
@WebServlet(name = "RestrictAccountServlet")
public class RestrictAccountServlet extends HttpServlet {
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

        Double manageridd =(Double) jsonObject.get("managerId");
        int managerid = manageridd.intValue();

        if(managerService.approvalManager(managerid,"BE_SEAL")){
            feedBack.setStatus(1);
            feedBack.setData(null);
        }else{
            feedBack.setStatus(5000);
            feedBack.setData(null);
        }

        PrintWriter pw = new PrintWriter(response.getWriter());
        pw.write(gson.toJson(feedBack));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
