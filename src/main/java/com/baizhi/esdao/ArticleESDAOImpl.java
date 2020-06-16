package com.baizhi.esdao;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleESDAOImpl implements ArticleESDAO {
    @Autowired
    private TransportClient transportClient;

    @Override
    public SearchResponse queryTermPage(String search, Integer page) throws Exception {
        //高亮配置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false)
                .preTags("<font color='red'>")
                .postTags("</font>")
                .field("*");
        //查询方式，查询源
        QueryStringQueryBuilder field = QueryBuilders.queryStringQuery(search).analyzer("ik_max_word")
                .field("title")
                .field("author")
                .field("content");
        //查询结果
        SearchResponse searchResponse = transportClient.prepareSearch("ems")
                .setTypes("article")
                .setQuery(field)
                .highlighter(highlightBuilder)
                .setFrom(page)
                .setSize(2)
                .get();
        return searchResponse;
    }
}
