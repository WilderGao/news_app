package com.qg.newsapp.service;

import com.qg.newsapp.dao.impl.ManagerDaoImpl;
import com.qg.newsapp.model.Manager;
import com.qg.newsapp.utils.MailUtil;
import com.qg.newsapp.utils.ManagerStatus;
import com.qg.newsapp.utils.StatusCode;

import java.util.HashMap;
import java.util.Map;


/**
 * 管理员逻辑判断类
 */
public class ManagerService {
    private ManagerDaoImpl dao = new ManagerDaoImpl();
    private static MailUtil mailUtil = new MailUtil();

    /**
     * 判断邮箱是否存在
     *
     * @param email 管理员邮箱
     * @return 存在--true，不存在--false
     */
    public boolean emailIsExist(String email) {
        if (dao.emailIsExist(email)) {
            return true;
        }
        return false;
    }

    /**
     * 判断邮箱格式是否正确
     *
     * @param email 管理员邮箱
     * @return 正确--true，错误--false
     */
    public boolean isRigthEmail(String email) {
        if (email == null
                || !email
                .matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 注册用户，并调用发送邮箱方法
     *
     * @param manager 管理员实体类
     * @return
     */
    public boolean register(Manager manager) {
        if (dao.addAccount(manager)) {
            sendActivationMail(manager.getManagerAccount(), manager.getManagerAccount());
            return true;
        } else {
            return false;
        }

    }

    /**
     * 发送邮件
     *
     * @param email 邮箱
     * @param code  唯一标识激活码（邮箱）
     */
    private void sendActivationMail(String email, String code) {
        String Subject = "激活邮件";
        String setContent = "<h1>此邮件为激活邮件！请点击下面链接完成激活操作！</h1><h3><a href='http://" +
                "192.168.43.142:8080/admin/verifyaccount?code="
                + code
                + "'>http://192.168.43.142:8080/admin/verifyaccount</a></h3>";
        String ContentType = "text/html;charset=UTF-8";
        try {
            mailUtil.sendMail(email, Subject, setContent, ContentType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送验证码至邮箱
     *
     * @param email     邮箱
     * @param checkcode 验证码
     */
    public void sendCheckcodeMail(String email, String checkcode) {
        String subject = "验证码邮件";
        String setContent = "<h1>" + checkcode + "</h1>";
        String contentType = "text/html;charset=UTF-8";
        try {
            mailUtil.sendMail(email, subject, setContent, contentType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断用户是否激活成功
     *
     * @param code 激活码
     * @return 激活成功——true，失败——false
     */
    public boolean active(String code) {
        int id = dao.queryManagerByCode(code);
        if (id != 0) {
            dao.updateStatus(id, ManagerStatus.UNAPPROVAL.getName());
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param manager
     * @return
     */
    public Map<Integer, Manager> login(Manager manager) {
        Manager manager1 = dao.login(manager);
        Map<Integer, Manager> map = new HashMap<>();

        if (manager1 == null) {
            map.put(StatusCode.PASSWORD_IS_ERROR.getStatusCode(), manager1); // 密码错误
        } else if (manager1.getManagerStatus().equals(ManagerStatus.UNACTIVATION.getName())) {
            map.put(StatusCode.ACCOUNT_IS_NOT_ACTIVE.getStatusCode(), manager1); // 账户未激活
        } else if (manager1.getManagerStatus().equals(ManagerStatus.UNAPPROVAL.getName())) {
            map.put(StatusCode.ACCOUNT_IS_NOT_APPROVED.getStatusCode(), manager1); // 账户未审批
        } else if (manager1.getManagerStatus().equals(ManagerStatus.BE_SEAL.getName())) {
            map.put(StatusCode.ACCOUNT_IS_CLOSED.getStatusCode(), manager1); // 账号被封
        } else {
            map.put(StatusCode.OK.getStatusCode(), manager1); // 正常
        }
        return map;
    }

    /**
     * 根据账号判断其状态
     * @param account 账号
     * @return 管理员状态
     */
    public int getManagerStatus(String account) {
        Manager manager = dao.getManagerByAccount(account);
        int state;
        if (manager.getManagerStatus().equals(ManagerStatus.NORMAL.getName())) {
            state = StatusCode.OK.getStatusCode(); // 正常
        } else if (manager.getManagerStatus().equals(ManagerStatus.BE_SEAL.getName())) {
            state = StatusCode.ACCOUNT_IS_CLOSED.getStatusCode(); // 被封号
        } else if (manager.getManagerStatus().equals(ManagerStatus.UNAPPROVAL.getName())) {
            state = StatusCode.ACCOUNT_IS_NOT_APPROVED.getStatusCode(); // 未审批
        } else {
            state = StatusCode.ACCOUNT_IS_NOT_ACTIVE.getStatusCode(); // 未激活
        }
        return state;
    }
}
