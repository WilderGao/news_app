package com.qg.newsapp.dao.impl;

import com.qg.newsapp.dao.FileDao;
import com.qg.newsapp.model.ViceFile;
import com.qg.newsapp.utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileDaoImpl implements FileDao {
    private Connection conn ;
    private PreparedStatement pstmt;
    private ResultSet resultSet;
    @Override
    public void addFile(ViceFile file){
        try {
        conn = JdbcUtil.getInstance().getConnection();
        String sql = "INSERT INTO vicefile (news_id,files_name,files_path,files_uuid)VALUE(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,file.getNewsId());
            pstmt.setString(2,file.getFileName());
            pstmt.setString(3,file.getFilePath());
            pstmt.setString(4,file.getFilesUUID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.free(conn,pstmt);
        }
    }

}
