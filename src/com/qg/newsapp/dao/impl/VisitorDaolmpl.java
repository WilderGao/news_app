package com.qg.newsapp.dao.impl;

import com.qg.newsapp.dao.VisitorDao;
import com.qg.newsapp.utils.JdbcUtil;
import com.qg.newsapp.utils.StatusCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VisitorDaolmpl implements VisitorDao {
    @Override
    public  int InsertVisitor(String visitorUUID){
        Connection conn;
        PreparedStatement pstmt;
        try {
            conn = JdbcUtil.getInstance().getConnection();
            String sql = "INSERT INTO visitor (visitor_uuid) value (?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,visitorUUID);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
            return StatusCode.OK.getStatusCode();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        }
        return StatusCode.Server_Error.getStatusCode();
    }
}
