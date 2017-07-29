package com.qg.newsapp.service;

import com.qg.newsapp.dao.impl.ManagerDaoImpl;
import com.qg.newsapp.model.Manager;

import java.util.List;
import java.util.Map;

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
        List<Manager> mg = managerDaoImpl.showUnapprovalManager();
        return mg;
    }

    public boolean approvalManager(int managerId,String status){
        ManagerDaoImpl managerDaoImpl = new ManagerDaoImpl();
        boolean result = managerDaoImpl.updateStatus(managerId,status);
        return result;
    }

    public boolean refuseManager(int managerId){
        ManagerDaoImpl managerDaoImpl = new ManagerDaoImpl();
        boolean result = managerDaoImpl.deleteAccount(managerId);
        return result;
    }
}
