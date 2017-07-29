package com.qg.newsapp.dao;

import com.qg.newsapp.model.Manager;

import java.util.List;

/**
 * Created by K Lin on 2017/7/28.
 */
public interface ManagerDao {
    public List<Manager> showManager();
    public List<Manager> showUnapprovalManager();
    public boolean updateStatus(int managerId,String status);
    public boolean deleteAccount(int managerId);
}
