package com.qg.newsapp.dao;

import com.qg.newsapp.model.News;

import java.util.List;

public interface NewsDao {
    /**
     * 刷新一次返回的新闻集合
     * @param startNews 从第几条新闻开始获得
     * @return 新闻的集合
     */
    public  abstract  List<News> GetNewsSummary(int startNews);

    /**
     * 增加新闻
     * @param  news 新闻类
     */
    public abstract void  AddNews(News news);
}
