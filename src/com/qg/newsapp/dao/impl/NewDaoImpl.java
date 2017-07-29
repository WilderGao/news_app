package com.qg.newsapp.dao.impl;

import com.qg.newsapp.dao.NewsDao;
import com.qg.newsapp.model.News;
import com.qg.newsapp.utils.JdbcUtil;

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
     * startNews    从第几条开始
     */
    public List<News> GetNewsSummary(int startNews) {
        try {
            List<News> newsList = new ArrayList<>();
            conn = JdbcUtil.getInstance().getConnection();
            String sql = "SELECT * FROM news ";
            int compareStart = 0 ;
            pstmt = conn.prepareStatement(sql);

            //得到结果集
            resultSet = pstmt.executeQuery();
            while (resultSet.next()){

                compareStart++;
                //跳过前面的几条新闻
                if (compareStart < startNews)
                    continue;

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
            resultSet.close();
            return newsList;

        } catch (SQLException e) {
            System.out.println("出现错误");
        }finally {
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


    //测试类
    public static void main(String[] args) {
        News news = new News();
        news.setFilesUUID("URIE");
        System.out.println(new NewDaoImpl().GetNewsId(news));
    }

}
