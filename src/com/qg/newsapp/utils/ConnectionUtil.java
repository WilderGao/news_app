package com.qg.newsapp.utils;


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * c3p0数据库连接池
 */
public class ConnectionUtil {
    private static ConnectionUtil instance;
    private static ComboPooledDataSource dataSource;

    private ConnectionUtil() throws PropertyVetoException {
        dataSource = new ComboPooledDataSource();

        //设置数据库的参数
        dataSource.setUser("root");
        dataSource.setPassword("970228");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/news_app");
        dataSource.setMinPoolSize(1);//设置最小连接数
        dataSource.setMaxPoolSize(10);
        dataSource.setMaxStatements(50);//最长等待时间
        dataSource.setMaxIdleTime(60);
    }

    //判断只建立一个连接池
    public static final ConnectionUtil getInstance() {
        if (instance == null) {
            try {
                instance = new ConnectionUtil();
            } catch (PropertyVetoException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return instance;
    }

    //创建连接
    public synchronized final Connection getConnection() {
        Connection conn = null;
        try {
            //这个getConnection和方法名含义
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    public void free(PreparedStatement st, Connection conn) {
        try {
            if (st != null) {
                st.close(); // 关闭Statement
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close(); // 关闭连接
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
