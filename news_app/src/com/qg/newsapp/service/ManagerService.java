package com.qg.newsapp.service;

import com.qg.newsapp.dao.impl.ManagerDaoImpl;
import com.qg.newsapp.model.Manager;
import com.qg.newsapp.model.News;

import java.util.List;

/**
 * Created by K Lin on 2017/7/28.
 */
public class ManagerService {
    public List<Manager> showManager() {
        ManagerDaoImpl managerDaoImpl = new ManagerDaoImpl();
        List<Manager> st =  managerDaoImpl.showManager();
        return st;
    }
    public List<Manager> showUnApprovalManager(){
        ManagerDaoImpl managerDaoImpl = new ManagerDaoImpl();
        List<Manager> unapprovalManager = managerDaoImpl.showUnapprovalManager();
        return unapprovalManager;
    }
    public List<News> showManagerOwnNews(int managerId){
        ManagerDaoImpl managerDaoImpl = new ManagerDaoImpl();
        List<News> ownNews = managerDaoImpl.showOwnNews(managerId);
        return ownNews;
    }

}
