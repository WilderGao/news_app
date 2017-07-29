package com.qg.newsapp.utils;


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * c3p0数据库连接池
 */
public class JdbcUtil {
    private static JdbcUtil instance;
    private static ComboPooledDataSource dataSource;

    private JdbcUtil() throws PropertyVetoException {
        dataSource = new ComboPooledDataSource();

        //设置数据库的参数
        dataSource.setUser("root");
        dataSource.setPassword("452tdjyninFF");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/news_app");
        dataSource.setMinPoolSize(1);//设置最小连接数
        dataSource.setMaxPoolSize(10);
        dataSource.setMaxStatements(50);//最长等待时间
        dataSource.setMaxIdleTime(60);
    }

    //判断只建立一个连接池
    public static final JdbcUtil getInstance() {
        if (instance == null) {
            try {
                instance = new JdbcUtil();
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

    public static void free(Connection conn, PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}