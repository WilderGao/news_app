package com.qg.newsapp.dao;

import com.qg.newsapp.model.Manager;
import com.qg.newsapp.model.News;

import java.util.List;

/**
 * Created by K Lin on 2017/7/28.
 */
public interface ManagerDao {
    /**
     * 展示所有状态为正常的管理员列表
     * @return 一个储存着管理员类的 List 集合
     */
    public List<Manager> showManager();

    /**
     * 展示所有状态为未审批的账号
     * @return 一个状态为未审批账号的 List 集合
     */
    public List<Manager> showUnapprovalManager();

    /**
     * 展示管理员自己所发的所有新闻
     * @return 一个管理员自己发新闻的 Lits 集合
     */
    public List<News> showOwnNews(int managerId);
}
