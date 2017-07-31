package com.qg.newsapp.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qg.newsapp.dao.impl.ManagerDaoImpl;
import com.qg.newsapp.model.FeedBack;
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
 * 接收验证码和新密码，设置新密码
 */
@WebServlet(name = "SetNewPasswordServlet", urlPatterns = "/admin/setnewpassword")
public class SetNewPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        FeedBack feedBack = new FeedBack();
        ManagerDaoImpl dao = new ManagerDaoImpl();
        ManagerService managerService = new ManagerService();

        // 获取JSON数据
        BufferedReader br = new BufferedReader(new
                InputStreamReader(request.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        Map<String, String> map = gson.fromJson(String.valueOf(sb), new TypeToken<Map<String, String>>() {
        }.getType());

        // 邮箱格式不正确
        if (!managerService.isRigthEmail(map.get("managerAccount"))) {
            feedBack.setState(StatusCode.EMAIL_FORMAT_IS_ERROR.getStatusCode());
            response.getWriter().write(gson.toJson(feedBack));
            return;
        }
        // 邮箱不存在
        if (!managerService.emailIsExist(map.get("managerAccount"))) {
            feedBack.setState(StatusCode.EMAIL_IS_NOT_EXIST.getStatusCode());
            response.getWriter().write(gson.toJson(feedBack));
            return;
        }
        int state = managerService.getManagerStatus(map.get("managerAccount")); // 获取账户状态
        if (state != StatusCode.OK.getStatusCode()) { // 不是正常状态
            feedBack.setState(state);
            response.getWriter().write(gson.toJson(feedBack));
            return;
        }

        String checkcode = (String) request.getSession().getAttribute(String.valueOf(map.get("managerAccount")));
        System.out.println(checkcode);
        if (checkcode.equals(map.get("verifyCode"))) {
            if (dao.updatePassword(map.get("managerAccount"),
                    map.get("managerPassword"))) {
                feedBack.setState(StatusCode.OK.getStatusCode()); // 正常
                response.getWriter().write(gson.toJson(feedBack));
            } else {
                feedBack.setState(StatusCode.Server_Error.getStatusCode()); // 服务器错误
                response.getWriter().write(gson.toJson(feedBack));
            }
            request.getSession().removeAttribute(String.valueOf(map.get("managerAccount")));
        } else {
            feedBack.setState(StatusCode.VERIFYCODE_IS_ERROR.getStatusCode()); // 验证码不正确
            response.getWriter().write(gson.toJson(feedBack));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
