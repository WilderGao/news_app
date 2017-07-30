package com.qg.newsapp.dao;

import com.qg.newsapp.model.News;

import java.util.List;

public interface NewsDao {
    /**
     * 刷新一次返回的新闻集合
     * @param news 从第几条新闻开始获得
     * @return 新闻的集合
     */
    public  abstract  List<News> GetNewsSummary(News news);

    /**
     * 增加新闻
     * @param  news 新闻类
     */
    public abstract void  AddNews(News news);


    /**
     * 获得这篇新闻对应的编号
     * @param news  新闻类
     * @return  新闻编号
     */
    public abstract int GetNewsId(News news);

    /**
     * 通过新闻Id获得关于这篇新闻的所有信息
     * @param newsId    新闻Id
     * @return  新闻类
     */
    public abstract  News GetNewsDetail(int newsId);

    public abstract int DeleteNews(int newsId);
}


