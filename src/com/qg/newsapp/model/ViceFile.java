package com.qg.newsapp.model;

public class ViceFile {
    private int fileId;     //附件Id
    private  int newsId;     //附件对应的新闻Id
    private  String fileName;   //附件名称
    private String filePath;     //文件保存路径
    private  String fileDownLoadTime;   //附件上传时间
    private  String filesUUID;      //附件的UUID

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileDownLoadTime() {
        return fileDownLoadTime;
    }

    public void setFileDownLoadTime(String fileDownLoadTime) {
        this.fileDownLoadTime = fileDownLoadTime;
    }

    public String getFilesUUID() {
        return filesUUID;
    }

    public void setFilesUUID(String filesUUID) {
        this.filesUUID = filesUUID;
    }
}
