package com.qg.newsapp.controller;

import com.google.gson.Gson;
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

@WebServlet(name = "ManagerLoginServlet", urlPatterns = "/admin/login")
public class ManagerLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        Manager manager;
        ManagerService managerService = new ManagerService();
        FeedBack feedBack = new FeedBack();
        ManagerDaoImpl dao = new ManagerDaoImpl();

        // 读取JSON数据
        BufferedReader br = new BufferedReader(new
                InputStreamReader(request.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        manager = gson.fromJson(String.valueOf(sb), Manager.class);
        // 如果用户名不存在
        if (!managerService.emailIsExist(manager.getManagerAccount())) {
            feedBack.setState(StatusCode.EMAIL_IS_NOT_EXIST.getStatusCode());
            response.getWriter().write(gson.toJson(feedBack));
            return;
        }

        Manager manager2 = dao.getManagerByAccount(manager.getManagerAccount());
        // 管理员已经登录，防止重复登录
        if (manager2.getOnline() == 1) {
            feedBack.setState(StatusCode.HAS_LOGIN.getStatusCode());
            response.getWriter().write(gson.toJson(feedBack));
        } else {
            Map<Integer, Manager> map = managerService.login(manager);
            Manager manager1 = map.get(1);
            System.out.println(manager1);
            dao.updateManagerLoginStatus(manager1.getManagerId(), 1);
            Integer integer = getKeyByValue(map, manager1);
            int state = Integer.valueOf(integer);
            feedBack.setData(gson.toJson(manager1));
            feedBack.setState(state);
            response.getWriter().write(gson.toJson(feedBack));
        }

    }

    private synchronized Integer getKeyByValue(Map<Integer, Manager> map, Manager manager) {
        for (Integer state : map.keySet()) {
            if (map.get(state) == manager || map.get(state).equals(manager)) {
                return state;
            }
        }
        return null;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
