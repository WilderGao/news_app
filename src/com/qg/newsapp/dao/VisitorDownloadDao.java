package com.qg.newsapp.dao;

import com.qg.newsapp.model.VisitorDownload;

public interface VisitorDownloadDao {
    /**
     * 将下载文件的人和文件名称增加到数据库中
     * @param visitorDownload   下载文件和类
     * @return  插入结果参数，正确返回1，错误返回5000
     */
    public  abstract int InsertVisitorDownLoad(VisitorDownload visitorDownload);


}
