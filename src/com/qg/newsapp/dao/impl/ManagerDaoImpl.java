package com.qg.newsapp.dao.impl;

import com.qg.newsapp.dao.ManagerDao;
import com.qg.newsapp.model.Manager;
import com.qg.newsapp.utils.ConnectionUtil;
import com.qg.newsapp.utils.ManagerStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDaoImpl implements ManagerDao {

    @Override
    public boolean emailIsExist(String email) {
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "select * from manager where manager_account = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            if (pstmt.executeQuery().next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.free(null, pstmt, conn);
        }
        return false;
    }

    @Override
    public boolean addAccount(Manager manager) {
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "insert into manager(manager_account, manager_password, " +
                    "manager_name, manager_super, manager_status) values (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, manager.getManagerAccount());
            pstmt.setString(2, manager.getManagerPassword());
            pstmt.setString(3, manager.getManagerName());
            pstmt.setInt(4, 0);
            pstmt.setString(5, ManagerStatus.UNACTIVATION.getName());
            if (pstmt.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.free(null, pstmt, conn);
        }
        return false;
    }

    @Override
    public int queryManagerByCode(String code) {
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String sql = "select manager_id from manager where manager_account = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, code);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.free(resultSet, pstmt, conn);
        }
        return 0;
    }

    @Override
    public boolean updateManagerStatus(int managerId, String status) {
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "update manager set manager_status = ? where manager_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, managerId);
            if (pstmt.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.free(null, pstmt, conn);
        }
        return false;
    }

    @Override
    public Manager login(Manager manager) {
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        Manager manager1 = new Manager();
        try {
            String sql = "select * from manager where manager_account = ? and manager_password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, manager.getManagerAccount());
            pstmt.setString(2, manager.getManagerPassword());
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                manager1.setManagerId(resultSet.getInt(1));
                manager1.setManagerAccount(resultSet.getString(2));
                manager1.setManagerPassword(resultSet.getString(3));
                manager1.setManagerName(resultSet.getString(4));
                manager1.setManagerSuper(resultSet.getInt(5));
                manager1.setManagerStatus(resultSet.getString(6));
                manager1.setOnline(resultSet.getInt(7));
            }
            return manager1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           connectionUtil.free(resultSet, pstmt, conn);
        }
        return null;
    }

    @Override
    public boolean updatePassword(String account, String password) {
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "update manager set manager_password = ? where manager_account = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setString(2, account);
            if (pstmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.free(null, pstmt, conn);
        }
        return false;
    }

    @Override
    public Manager getManagerByAccount(String account) {
        Manager manager = new Manager();
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String sql = "select * from manager where manager_account = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                manager.setManagerId(resultSet.getInt(1));
                manager.setManagerAccount(resultSet.getString(2));
                manager.setManagerPassword(resultSet.getString(3));
                manager.setManagerName(resultSet.getString(4));
                manager.setManagerSuper(resultSet.getInt(5));
                manager.setManagerStatus(resultSet.getString(6));
                manager.setOnline(resultSet.getInt(7));
                return manager;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.free(resultSet, pstmt, conn);
        }
        return null;
    }

    @Override
    public boolean superManagerAddManager(Manager manager) {
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "insert into manager(manager_account, manager_password, " +
                    "manager_name, manager_super, manager_status) values (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, manager.getManagerAccount());
            pstmt.setString(2, manager.getManagerPassword());
            pstmt.setString(3, manager.getManagerName());
            pstmt.setInt(4, 0);
            pstmt.setString(5, ManagerStatus.NORMAL.getName());
            if (pstmt.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.free(null, pstmt, conn);
        }
        return false;
    }

    @Override
    public Manager getManagerById(int id) {
        Manager manager = new Manager();
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String sql = "select * from manager where manager_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                manager.setManagerId(resultSet.getInt(1));
                manager.setManagerAccount(resultSet.getString(2));
                manager.setManagerPassword(resultSet.getString(3));
                manager.setManagerName(resultSet.getString(4));
                manager.setManagerSuper(resultSet.getInt(5));
                manager.setManagerStatus(resultSet.getString(6));
                manager.setOnline(resultSet.getInt(7));
                return manager;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           connectionUtil.free(resultSet, pstmt, conn);
        }
        return null;
    }

    @Override
    public void deleteManagerById(int id) {
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "delete from manager where manager_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.free(null, pstmt, conn);
        }

    }

    @Override
    public void updateManagerLoginStatus(int id, int loginStatus) {
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Connection conn = connectionUtil.getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "update manager set manager_online = ? where manager_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, loginStatus);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionUtil.free(null, pstmt, conn);
        }
    }
}
