package com.qg.newsapp.dao.impl;

import com.qg.newsapp.dao.ManagerDao;
import com.qg.newsapp.model.Manager;
import com.qg.newsapp.utils.JdbcUtil;
import com.qg.newsapp.utils.ManagerStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDaoImpl implements ManagerDao {

    @Override
    public boolean emailIsExist(String email) {
        Connection conn = JdbcUtil.getConnection();
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
            JdbcUtil.close(pstmt, conn);
        }
        return false;
    }

    @Override
    public boolean addAccount(Manager manager) {
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "insert into manager(manager_account, manager_password, " +
                    "manager_name, manager_super, manager_status) values (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, manager.getManagerAccount());
            pstmt.setString(2, manager.getManagerPassword());
            pstmt.setString(3, manager.getManagerName());
            pstmt.setInt(4,0);
            pstmt.setString(5, ManagerStatus.UNACTIVATION.getName());
            if (pstmt.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(pstmt, conn);
        }
        return false;
    }

    @Override
    public int queryManagerByCode(String code) {
        Connection conn = JdbcUtil.getConnection();
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
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JdbcUtil.close(pstmt, conn);
            }

        }
        return 0;
    }

    @Override
    public void updateStatus(int managerId, String status) {
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement pstmt = null;
        try {
            String sql = "update manager set manager_status = ? where manager_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, managerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(pstmt, conn);
        }
    }

    @Override
    public Manager login(Manager manager) {
        Connection conn = JdbcUtil.getConnection();
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
            }
            return manager1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JdbcUtil.close(pstmt, conn);
            }
        }
        return null;
    }

    @Override
    public boolean updatePassword(String account, String password) {
        Connection conn = JdbcUtil.getConnection();
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
            JdbcUtil.close(pstmt, conn);
        }
        return false;
    }
}
