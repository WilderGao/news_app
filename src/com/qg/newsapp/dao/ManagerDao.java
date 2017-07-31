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
     * @return 成功——true，失败——false
     */
    boolean updateManagerStatus(int managerId, String status);

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
     * @param account 账号
     * @return Manager实体类
     */
    Manager getManagerByAccount(String account);

    /**
     * 超级管理员添加普通管理员
     *
     * @param manager Manager实体类
     * @return 成功——true，失败——false
     */
    boolean superManagerAddManager(Manager manager);

    /**
     * 根据id查找管理员
     * @param id 管理员id
     * @return 管理员实体类
     */
    Manager getManagerById(int id);

    /**
     * 根据id删除管理员
     * @param id 管理员id
     */
    void deleteManagerById(int id);

    /**
     * 修改管理员的登录状态
     * @param id 管理员id
     * @param loginStatus 登录状态，1为登录，0为为登录
     */
    void updateManagerLoginStatus(int id, int loginStatus);
}
