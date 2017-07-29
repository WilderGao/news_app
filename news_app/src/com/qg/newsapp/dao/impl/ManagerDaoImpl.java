package com.qg.newsapp.dao.impl;

import com.qg.newsapp.dao.ManagerDao;
import com.qg.newsapp.model.Manager;
import com.qg.newsapp.utils.ConnectionUtil;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qg.newsapp.utils.ManagerStatus.NORMAL;

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
            stmt = conn.prepareStatement("SELECT * FROM manager WHERE manager_status ='NORMAL'");
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
            stmt = conn.prepareStatement("SELECT * FROM manager WHERE manager_status ='UNAPPROVAL'");
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
    public boolean updateStatus(int managerId,String status){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean ok = false;
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();

        try {
            stmt = conn.prepareStatement("UPDATE manager SET manager_status=? WHERE manager_id='"+managerId+"'");
            stmt.setString(1,status);
            stmt.execute();
            stmt.clearParameters();

            ok=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionUtil.free(rs,stmt,conn);
        }
        return ok;
    }
    public boolean deleteAccount(int managerId){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean ok = false;
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();
        try{
            stmt=conn.prepareStatement("DELETE from manager where manager_id='"+managerId+"'");
            stmt.executeUpdate();
            stmt.clearParameters();

            ok=true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }finally{
            connectionUtil.free(rs, stmt, conn);
        }
        return ok;
    }
}
