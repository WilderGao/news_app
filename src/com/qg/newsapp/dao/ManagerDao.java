package com.qg.newsapp.dao;

import com.qg.newsapp.model.Manager;

/**
 * 管理员DAO层
 */
public interface ManagerDao {

    /**
     * 根据邮箱判断该邮箱是否存在
     *
     * @param email 管理员邮箱
     * @return 存在--true，不存在--false
     */
    boolean emailIsExist(String email);

    /**
     * 注册管理员
     *
     * @param manager 管理员实体类
     * @return 成功--true，失败--false
     */
    boolean addAccount(Manager manager);

    /**
     * 根据激活码查找管理员
     *
     * @param code 激活码
     * @return 管理员id
     */
    int queryManagerByCode(String code);

    /**
     * 更新管理员的状态
     *
     * @param managerId 管理员id
     * @param status    管理员状态
     */
    void updateStatus(int managerId, String status);

    /**
     * 登录
     *
     * @param manager 管理员实体类
     * @return 一个Manager实体类
     */
    Manager login(Manager manager);

    /**
     * 修改密码
     *
     * @param account  账户
     * @param password 密码
     * @return 成功——true，失败——false
     */
    boolean updatePassword(String account, String password);

    /**
     * 根据账号查找账户
     *
     * @param account
     * @return
     */
    Manager getManagerByAccount(String account);
}
