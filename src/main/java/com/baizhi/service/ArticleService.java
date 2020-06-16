package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    void insertArticle(Article article);

    void deleteArticle(Article article);

    void deleteArticles(String[] id);

    void updateArticle(String id, Article article);

    List<Article> selectArticlesAll();//查询所有文章

    Map<String, Object> selectArticlesPage(Integer page, Integer rows);//查询所有文章并分页

    Integer selectArticleCount();//查询所有文章的篇数

    //List<Article> selectArticlesByGuruid(String guruid);//根据上师id查询文章
    Article selectArticleById(String id);//根据id精准查找文章

    List<Map> queryTermPage(String search, Integer page) throws Exception;

    //List<Article> queryByHighLight(String search);//springdate高亮查询
}
