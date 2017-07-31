package com.qg.newsapp.dao.impl;

import com.qg.newsapp.dao.NewsDao;
import com.qg.newsapp.model.News;
import com.qg.newsapp.model.ViceFile;
import com.qg.newsapp.utils.JdbcUtil;
import com.qg.newsapp.utils.StatusCode;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class NewDaoImpl implements NewsDao {
    private static final int  RREQUEST_NUM = 10;
    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet resultSet;

    @Override
    /**
     * 获得从第几条开始的10条新闻
     * newGet  从第几条新闻获得
     */
    public List<News> GetNewsSummary(News newsGet) {
        try {
            List<News> newsList = new ArrayList<>();
            conn = JdbcUtil.getInstance().getConnection();
            String sql = "SELECT * FROM news WHERE news_id < ? ORDER BY news_id DESC ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,newsGet.getNewsId());
            //得到结果集
            resultSet = pstmt.executeQuery();
            while (resultSet.next()){

                //获得从中得到的信息
                News news = new News();
                news.setNewsId(resultSet.getInt("news_id"));
                news.setNewsAuthor(resultSet.getString("news_author"));
                news.setNewsTitle(resultSet.getString("news_title"));
                news.setNewsTime(resultSet.getString("news_time"));
                news.setNewsFace(resultSet.getString("news_facepath"));

                //因为只是得到新闻概要所以不要附加上内容和附件信息
                newsList.add(news);

                //超过10条就退出循环
                if (newsList.size()>=RREQUEST_NUM)
                    break;

            }
            return newsList;

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

        return  null;
    }

    /**
     * 增加新闻
     * @param news 新闻类
     */
    public void AddNews(News news){
        try {
            conn = JdbcUtil.getInstance().getConnection();
            String sql = "INSERT INTO news (news_title,news_mainbody,news_author,news_time,news_facepath,files_uuid,manager_id) VALUE (?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,news.getNewsTitle());
            pstmt.setString(2,news.getNewsBody());
            pstmt.setString(3,news.getNewsAuthor());
            pstmt.setString(4,news.getNewsTime());
            pstmt.setString(5,news.getNewsFace());
            pstmt.setString(6,news.getFilesUUID());
            pstmt.setInt(7,news.getManagerId());

            //更新数据
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.free(conn,pstmt);
        }

    }

    public void UpdateNews(News news){
        try {
            conn = JdbcUtil.getInstance().getConnection();
            String sql = "UPDATE news SET news_facepath = ? WHERE news_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,news.getNewsFace());
            pstmt.setInt(2,news.getNewsId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.free(conn,pstmt);
        }
    }

    /**
     * 获得新闻对应的编号
     * @param news 新闻类
     * @return
     */
    public  int GetNewsId(News news){
        try {
            conn = JdbcUtil.getInstance().getConnection();
            String sql = "SELECT news_id FROM news WHERE files_uuid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,news.getFilesUUID());
            resultSet = pstmt.executeQuery();
            int getNewsId = 0;
            while (resultSet.next()){
                getNewsId = resultSet.getInt("news_id");
            }
            return getNewsId;
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
        return  0;
    }


    /**
     * 通过新闻Id获得关于这篇新闻的所有信息
     * @param newsId    新闻Id
     * @return
     */
    public synchronized News GetNewsDetail(int newsId){
        News news = new News();
        Connection conns = JdbcUtil.getInstance().getConnection();
        PreparedStatement pstmts = null;

        List<ViceFile> viceFileList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM news WHERE news_id = ?";
            pstmts = conns.prepareStatement(sql);
            pstmts.setInt(1,newsId);

            ResultSet resultSets = pstmts.executeQuery();

            if (resultSets.next()) {
                news.setNewsTitle(resultSets.getString("news_title"));
                news.setNewsAuthor(resultSets.getString("news_author"));
                news.setNewsBody(resultSets.getString("news_mainbody"));
                news.setManagerId(resultSets.getInt("manager_id"));
                news.setNewsId(resultSets.getInt("news_id"));
                news.setNewsFace(resultSets.getString("news_facepath"));
            }
            if (conns.isClosed())
                conns = JdbcUtil.getInstance().getConnection();
            resultSets.close();

            String sqlSecond = "SELECT * FROM vicefile WHERE news_id = ?";
            pstmts = conns.prepareStatement(sqlSecond);
            pstmts.setInt(1,newsId);
            ResultSet resultSetSecond = pstmts.executeQuery();
            while (resultSetSecond.next()){
                ViceFile viceFile = new ViceFile();
                    viceFile.setFileId(resultSetSecond.getInt("files_id"));
                    viceFile.setFileName(resultSetSecond.getString("files_name"));
                    viceFile.setFilePath(resultSetSecond.getString("files_path"));
                    viceFile.setFilesUUID(resultSetSecond.getString("files_uuid"));
                    viceFile.setNewsId(resultSetSecond.getInt("news_id"));
                    viceFileList.add(viceFile);
            }
            resultSetSecond.close();
            news.setFileList(viceFileList);
            return news;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pstmts.close();
                conns.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return  null;

    }

    /**
     * 通过新闻Id删除对应的新闻
     * @param newsId 新闻Id
     * @return  返回操作是否正常，正常返回1，错误返回5000
     */
    public int DeleteNews(int newsId){
        try {
        conn = JdbcUtil.getInstance().getConnection();
        String sql = "DELETE news,vicefile FROM vicefile , news WHERE news.news_id = vicefile.news_id AND news.news_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,newsId);
            //删除更新数据库
            pstmt.executeUpdate();
            return StatusCode.OK.getStatusCode();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.free(conn,pstmt);
        }
        return StatusCode.Server_Error.getStatusCode();
    }

    //同步锁
    public synchronized List<News> GetCurrentNews(){
        try {
            List<News> newsList = new ArrayList<>();

            conn = JdbcUtil.getInstance().getConnection();
            String sql = "SELECT * FROM news  ORDER BY news_id DESC";
            pstmt = conn.prepareStatement(sql);
            int recordNum = 0;

             resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                News news = new News();
                news.setNewsTitle(resultSet.getString("news_title"));
                news.setNewsAuthor(resultSet.getString("news_author"));
                news.setNewsId(resultSet.getInt("news_id"));
                news.setNewsFace(resultSet.getString("news_facepath"));
                news.setNewsBody(resultSet.getString("news_mainbody"));
                news.setManagerId(resultSet.getInt("manager_id"));
                news.setNewsTime(resultSet.getString("news_time"));

                recordNum++;
                if (recordNum>=10)
                    break;
                newsList.add(news);
            }

            return  newsList;
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
