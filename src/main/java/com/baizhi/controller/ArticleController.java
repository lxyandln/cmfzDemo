package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ResponseBody
    @RequestMapping("/selectArticlesPage")
    public Map<String, Object> selectArticlesPage(Integer page, Integer rows) {
        Map<String, Object> map = articleService.selectArticlesPage(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("/insertArticle")
    public void insertArticle(Article article) {
        System.out.println("article+:" + article);
        articleService.insertArticle(article);
    }

    @ResponseBody
    @RequestMapping("/updateArticle")
    public void updateArticle(String id, Article article) {
        System.out.println("id:" + id);
        System.out.println("article+:+" + article);
        articleService.updateArticle(id, article);
    }

    @ResponseBody
    @RequestMapping("/deletesArticle")
    public void deletesArticle(String[] id) {
        articleService.deleteArticles(id);
    }

    /*@ResponseBody
    @RequestMapping("edit")
    public void edit(Guru guru, String oper) {
        if (oper.equals("add")) {
            guruService.insertGuru(guru);
        } else if (oper.equals("edit")) {
            guruService.updateGuru(guru);
        } else if (oper.equals("del")) {
            guruService.deleteGuru(guru);
        }
    }*/

    @RequestMapping("/queryTermPage")
    @ResponseBody
    public List<Map> queryTermPage(String search, int page) throws Exception {
        List<Map> list = articleService.queryTermPage(search, (page - 1) * 5);
        return list;
    }

    /*@RequestMapping("/queryByHighLight")
    @ResponseBody
    public List<Article> queryByHighLight(String search){//springdate高亮查询
        List<Article> list = articleService.queryByHighLight(search);
        return list;
    }*/
}
