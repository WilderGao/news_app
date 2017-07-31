package com.qg.newsapp.dao;

import com.qg.newsapp.model.ViceFile;
import com.qg.newsapp.model.VisitorDownload;

import java.util.List;


public interface VisitorDownloadDao {
    /**
     * 将下载文件的人和文件名称增加到数据库中
     * @param visitorDownload   下载文件和类
     * @return  插入结果参数，正确返回1，错误返回5000
     */
    public  abstract int InsertVisitorDownLoad(VisitorDownload visitorDownload);

    /**
     * 通过附件Id找到所有下载这个附件的人名和下载时间
     * @param filesId   附件Id
     * @return  所有下载这个附件的人名和时间集合
     */
    public abstract List<VisitorDownload> VicefileDownloadDetail(int filesId);


    /**
     * 通过管理员的Id得到关于它的所有附件信息
     * @param managerId 管理员Id
     * @return  所有关于这个管理员的附件信息
     */
    public abstract List<ViceFile> GetViceFileList(int managerId);


}
