package com.qg.newsapp.model;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * 新闻类
 */
public class News {
    private  int newsId;     // 新闻Id
    private int managerId;          //发新闻的人的Id
    private  String newsTitle ;     // 新闻标题
    private String newsBody;     //新闻主要内容
    private String newsAuthor;      // 新闻作者
    private  String newsTime ;      //新闻发布时间
    private String newsUUID;        //新闻UUID
    private String newsFace;        //新闻封面路径
    private String filesUUID;     //附件UUID
    private  List<ViceFile> fileList;   //一个新闻对应的附件集合

    public String getNewsUUID() {
        return newsUUID;
    }

    public void setNewsUUID(String newsUUID) {
        this.newsUUID = newsUUID;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public void setNewsBody(String newsBody) {
        this.newsBody = newsBody;
    }

    public String getFilesUUID() {
        return filesUUID;
    }

    public void setFilesUUID(String filesUUID) {
        this.filesUUID = filesUUID;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }


    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsFace() {
        return newsFace;
    }

    public void setNewsFace(String newsFace) {
        this.newsFace = newsFace;
    }

    public List<ViceFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ViceFile> fileList) {
        this.fileList = fileList;
    }


}
