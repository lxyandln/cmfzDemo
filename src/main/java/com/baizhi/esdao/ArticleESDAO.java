package com.baizhi.esdao;

import org.elasticsearch.action.search.SearchResponse;

public interface ArticleESDAO {
    SearchResponse queryTermPage(String search, Integer page) throws Exception;
}
