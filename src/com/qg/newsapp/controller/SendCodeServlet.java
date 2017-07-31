package com.qg.newsapp.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.util.Random;

/**
 * 生成激活码并发送到邮箱
 */
@WebServlet(name = "SendCodeServlet", urlPatterns = "/admin/sendverifycode")
public class SendCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        ManagerService managerService = new ManagerService();
        FeedBack feedBack = new FeedBack();
        Map<String, String> map;
        // 读取JSON数据
        BufferedReader br = new BufferedReader(new
                InputStreamReader(request.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        map = gson.fromJson(String.valueOf(sb), new TypeToken<Map<String, String>>() {
        }.getType());
        System.out.println(map);
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
        String checkcode = getCheckcode();
        managerService.sendCheckcodeMail(map.get("managerAccount"),checkcode);
        feedBack.setState(StatusCode.OK.getStatusCode());
        //request.getSession().setMaxInactiveInterval(60 * 60); // Session过期时间为一个小时
        request.getSession().setAttribute(map.get("managerAccount"), checkcode); // 存进Session
        System.out.println(feedBack);
        response.getWriter().write(gson.toJson(feedBack));
    }

    /**
     * 获得随机的验证码
     *
     * @return 验证码字符串
     */
    private String getCheckcode() {
        String content = "1234567890";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(content.length());
            char letter = content.charAt(index);
            stringBuffer.append(letter);
        }
        return stringBuffer.toString();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


}
