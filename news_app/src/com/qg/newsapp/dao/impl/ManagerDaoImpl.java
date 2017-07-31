package com.qg.newsapp.dao.impl;

import com.qg.newsapp.dao.ManagerDao;
import com.qg.newsapp.model.Manager;
import com.qg.newsapp.model.News;
import com.qg.newsapp.utils.ConnectionUtil;
import com.qg.newsapp.utils.ManagerStatus;

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
                manager.setManagerPassword(rs.getString("manager_password"));
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
            try{
                if(rs!=null){
                    rs.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                connectionUtil.free(stmt, conn);
            }
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
            try{
                if(rs!=null){
                    rs.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                connectionUtil.free(stmt, conn);
            }
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
            try{
                if(rs!=null){
                    rs.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                connectionUtil.free(stmt, conn);
            }
        }
        return null;
    }
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
            connectionUtil.free(pstmt, conn);
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
            connectionUtil.free(pstmt, conn);
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
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectionUtil.free(pstmt, conn);
            }
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
            connectionUtil.free(pstmt, conn);
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
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectionUtil.free(pstmt, conn);
            }
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
            connectionUtil.free(pstmt, conn);
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
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectionUtil.free(pstmt, conn);
            }
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
            connectionUtil.free(pstmt, conn);
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
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectionUtil.free(pstmt, conn);
            }
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
            connectionUtil.free(pstmt, conn);
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
            connectionUtil.free(pstmt, conn);
        }
    }
}
