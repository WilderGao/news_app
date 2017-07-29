package com.qg.newsapp.dao;

import com.qg.newsapp.model.News;

import java.util.List;

public interface NewsDao {
    public  abstract  List<News> GetNewsSummary(int startNews);
    public abstract void  AddNews(News news);
}
