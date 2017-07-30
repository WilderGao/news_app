package com.qg.newsapp.dao.impl;

import com.qg.newsapp.dao.VisitorDownloadDao;
import com.qg.newsapp.model.VisitorDownload;
import com.qg.newsapp.utils.JdbcUtil;
import com.qg.newsapp.utils.StatusCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class VisitorDownloadDaolmpl implements VisitorDownloadDao{
    Connection conn ;
    PreparedStatement pstmt ;
    ResultSet resultSet ;
    @Override
    public int InsertVisitorDownLoad(VisitorDownload visitorDownload){
        try {
            conn = JdbcUtil.getInstance().getConnection();
            String sql = "INSERT INTO visitordownload (files_id,downloader) VALUE (?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,visitorDownload.getFileId());
            pstmt.setString(2,visitorDownload.getDownloader());

            //更新到数据库上
            pstmt.executeUpdate();
            return StatusCode.OK.getStatusCode();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcUtil.free(conn,pstmt);
        }
        return StatusCode.Server_Error.getStatusCode();
    }

}
