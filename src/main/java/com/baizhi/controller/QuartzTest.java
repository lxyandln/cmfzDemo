/*package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
//@Scope("prototype")
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class QuartzTest {
    *//*@Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
    public static void work() throws Exception {
        System.out.println("执行调度任务："+new Date());
    }


    @Scheduled(fixedRate = 5000)//每5秒执行一次
    public static void play() throws Exception {
        System.out.println("执行Quartz定时器任务："+new Date());
    }*//*

    @Autowired
    private TransportClient transportClient;
    @Autowired
    private ArticleService articleService;
    @Scheduled(cron = "0/2 * * * * ?") //每2秒执行一次
    public void doSomething() throws Exception {
        System.out.println("每2秒执行一个的定时任务："+new Date());
        List<Article> articles = articleService.selectArticlesAll();
        for (Article article : articles) {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
            XContentBuilder xContentBuilder1 = xContentBuilder.startObject()
                    .field("id", article.getId())
                    .field("title", article.getTitle())
                    .field("author", article.getAuthor())
                    .field("content", article.getContent())
                    .field("state", article.getState())
                    .field("time", article.getTime())
                    .endObject();
            transportClient.prepareIndex("cmfz","article",article.getId()).setSource(xContentBuilder1).get();
            System.out.println("article："+article);
        }

    }

    *//*@Scheduled(cron = "0 0 0/1 * * ? ") // 每一小时执行一次
    public static void goWork() throws Exception {
        System.out.println("每一小时执行一次的定时任务："+new Date());
    }

    @Scheduled(cron = "0 37 17 * * ?") // 到17点37执行
    public static void date() throws Exception {
        System.out.println("到了17点37执行："+new Date());
    }*//*
}*/
