package com.qg.newsapp.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qg.newsapp.dao.impl.ManagerDaoImpl;
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
import java.util.Map;

/**
 * 需要判断用户状态
 */
@WebServlet(name = "SetNewPasswordServlet", urlPatterns = "/admin/setnewpassword")
public class SetNewPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        FeedBack feedBack = new FeedBack();
        ManagerDaoImpl dao = new ManagerDaoImpl();

        BufferedReader br = new BufferedReader(new
                InputStreamReader(request.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        Map<String, Object> map = gson.fromJson(String.valueOf(sb), new TypeToken<Map<String, Object>>(){}.getType());
        System.out.println(map);
        String checkcode = (String) request.getSession().getAttribute(String.valueOf(map.get("managerAccount")));
        if (checkcode.equals(map.get("verifyCode"))) {
            if (dao.updatePassword((String) map.get("managerAccount"),
                    (String) map.get("managerPassword"))) {
                feedBack.setStatus(StatusCode.OK.getStatusCode());
                response.getWriter().write(gson.toJson(feedBack));
            } else {
                feedBack.setStatus(StatusCode.Server_Error.getStatusCode());
                response.getWriter().write(gson.toJson(feedBack));
            }
        } else {
            feedBack.setStatus(StatusCode.VERIFYCODE_IS_ERROR.getStatusCode());
            response.getWriter().write(gson.toJson(feedBack));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
