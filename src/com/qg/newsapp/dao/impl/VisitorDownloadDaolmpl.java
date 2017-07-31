package com.qg.newsapp.dao.impl;

import com.qg.newsapp.dao.VisitorDownloadDao;
import com.qg.newsapp.model.ViceFile;
import com.qg.newsapp.model.VisitorDownload;
import com.qg.newsapp.service.DownloaderJudgeService;
import com.qg.newsapp.utils.JdbcUtil;
import com.qg.newsapp.utils.StatusCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VisitorDownloadDaolmpl implements VisitorDownloadDao{
    Connection conn ;
    PreparedStatement pstmt ;
    ResultSet resultSet ;
    @Override
    public int InsertVisitorDownLoad(VisitorDownload visitorDownload){
        try {
            conn = JdbcUtil.getInstance().getConnection();
            String sql = "INSERT INTO visitordownload (files_id,downloader,download_time) VALUE (?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,visitorDownload.getFileId());
            pstmt.setString(2,visitorDownload.getDownloader());
            pstmt.setString(3,visitorDownload.getDownloadTime());

            //更新到数据库上
            pstmt.executeUpdate();

            //插入数据库中之后让附件表中该附件的下载次数+1
            String sqlSecond = "UPDATE vicefile SET downloadtime = downloadtime+1";
            pstmt = conn.prepareStatement(sqlSecond);
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

    public  List<VisitorDownload> VicefileDownloadDetail(int filesId){
        List<VisitorDownload> visitorDownloadList = new ArrayList<>();
        String downloaderName;
        try {
            conn = JdbcUtil.getInstance().getConnection();
            String sql = "SELECT downloader FROM visitordownload WHERE files_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,filesId);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                VisitorDownload visitorDownload = new VisitorDownload();
                downloaderName = resultSet.getString("downloader");
                //判断是不是管理员，账号满足邮箱格式
                boolean result = DownloaderJudgeService.IsManager(downloaderName);
                if (result == true){
                    String sqlSecond = "SELECT manager_name FROM manager WHERE manager_account = ?";
                    pstmt = conn.prepareStatement(sqlSecond);
                    pstmt.setString(1,downloaderName);
                    ResultSet resultSet1 = pstmt.executeQuery();
                    if (resultSet1.next())
                        visitorDownload.setDownloader("管理员"+resultSet1.getString("manager_name"));
                    //最后将结果集关闭
                    resultSet1.close();
                }else {
                    String sqlThird = "SELECT visitor_id FROM visitor WHERE visitor_uuid = ?";
                    pstmt = conn.prepareStatement(sqlThird);
                    pstmt.setString(1,downloaderName);
                    ResultSet resultSet2 = pstmt.executeQuery();
                    if (resultSet2.next())
                        visitorDownload.setDownloader("游客"+resultSet2.getInt("visitor_id"));
                    resultSet2.close();
                }
                //加入下载的时间
                visitorDownload.setDownloadTime(resultSet.getString("download_time"));
                //最后将这个类加入到集合中
                visitorDownloadList.add(visitorDownload);

            }
            return visitorDownloadList;
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
        return null;
    }

    public  List<ViceFile> GetViceFileList(int managerId){
        List<ViceFile> viceFileList = new ArrayList<>();
        try {
            conn = JdbcUtil.getInstance().getConnection();
            String sql = "SELECT * FROM vicefile LEFT JOIN news ON vicefile.news_id = news.news_id WHERE news.manager_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,managerId);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                ViceFile viceFile = new ViceFile();
                viceFile.setFileDownLoadTime(resultSet.getInt("downloadtime"));
                viceFile.setFileName(resultSet.getString("files_name"));
                viceFile.setFileId(resultSet.getInt("files_id"));
                //将它插入集合中
                viceFileList.add(viceFile);
            }
            return viceFileList;
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
        return null;

    }


}
