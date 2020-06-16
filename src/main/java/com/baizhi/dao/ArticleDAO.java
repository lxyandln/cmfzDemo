package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*@Mapper
@CacheNamespace(implementation = MyCache.class)*/
public interface ArticleDAO {
    void insertArticle(Article article);

    void deleteArticle(Article article);

    void deleteArticles(String[] id);

    void updateArticle(Article article);

    List<Article> selectArticlesAll();//查询所有文章

    List<Article> selectArticlesPage(@Param(value = "start") Integer start, @Param(value = "rows") Integer rows);//查询所有文章并分页

    Integer selectArticleCount();//查询所有文章的篇数

    //List<Article> selectArticlesByGuruid(String guruid);//根据上师id查询文章
    Article selectArticleById(String id);//根据id精准查找文章
}
