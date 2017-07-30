package com.qg.newsapp.dao.impl;

import com.qg.newsapp.dao.ManagerDao;
import com.qg.newsapp.model.Manager;
import com.qg.newsapp.model.News;
import com.qg.newsapp.utils.ConnectionUtil;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by K Lin on 2017/7/28.
 */
public class ManagerDaoImpl implements ManagerDao {
    public List<Manager> showManager() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getInstance().getConnection();
        try {
            stmt = conn.prepareStatement("SELECT * FROM manager WHERE manager_status ='正常'");
            stmt.clearParameters();

            rs = stmt.executeQuery();
            List<Manager> result = new ArrayList<Manager>();

            while (rs.next()) {
                Manager manager = new Manager();

                manager.setManagerId(rs.getInt("manager_id"));
                manager.setManagerAccount(rs.getString("manager_account"));
                manager.setManagerPassword(null);
                manager.setManagerName(rs.getString("manager_name"));
                manager.setManagerSuper(rs.getInt("manager_super"));
                manager.setManagerStatus(rs.getString("manager_status"));
                manager.setNewsList(null);

                result.add(manager);
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.free(rs, stmt, conn);
        }
        return null;
    }
    public List<Manager> showUnapprovalManager(){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getInstance().getConnection();
        try {
            stmt = conn.prepareStatement("SELECT * FROM manager WHERE manager_status ='未审批'");
            stmt.clearParameters();

            rs = stmt.executeQuery();
            List<Manager> result = new ArrayList<Manager>();

            while (rs.next()) {
                Manager manager = new Manager();

                manager.setManagerId(rs.getInt("manager_id"));
                manager.setManagerAccount(rs.getString("manager_account"));
                manager.setManagerPassword(null);
                manager.setManagerName(rs.getString("manager_name"));
                manager.setManagerStatus(rs.getString("manager_status"));
                manager.setNewsList(null);

                result.add(manager);
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.free(rs, stmt, conn);
        }
        return null;
    }

    public List<News> showOwnNews(int managerId){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getInstance().getConnection();
        try {
            stmt = conn.prepareStatement("SELECT * FROM news WHERE manager_id = '"+managerId+"'");
            stmt.clearParameters();

            rs = stmt.executeQuery();
            List<News> result = new ArrayList<News>();
            while (rs.next()) {
                News news = new News();

                news.setNewsId(rs.getInt("news_id"));
                news.setNewsTitle(rs.getString("news_title"));
                news.setNewsFace(rs.getString("news_facepath"));
                news.setNewsTime(rs.getString("news_time"));

                result.add(news);
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.free(rs, stmt, conn);
        }
        return null;
    }
}
