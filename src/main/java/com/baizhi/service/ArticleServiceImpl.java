package com.baizhi.service;

import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private TransportClient transportClient;
    @Autowired
    private ArticleDAO articleDAO;
    //@Autowired
    //private ElasticsearchTemplate elasticsearchTemplate;//springdate

    @Override
    public void insertArticle(Article article) {
        article.setId(UUID.randomUUID().toString())
                .setTime(new Date());
        articleDAO.insertArticle(article);
    }

    @Override
    public void deleteArticle(Article article) {
        articleDAO.deleteArticle(article);
    }

    @Override
    public void deleteArticles(String[] id) {
        articleDAO.deleteArticles(id);
    }

    @Override
    public void updateArticle(String id, Article article) {
        article.setId(id);
        articleDAO.updateArticle(article);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Article> selectArticlesAll() {
        List<Article> list = articleDAO.selectArticlesAll();
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectArticlesPage(Integer page, Integer rows) {
        int start = (page - 1) * rows;
        Map<String, Object> map = new HashMap<>();
        Integer count = articleDAO.selectArticleCount();
        long total = (count - 1) / rows + 1;
        List<Article> articles = articleDAO.selectArticlesPage(start, rows);
        map.put("rows", articles);//分页所需集合
        map.put("page", page);//当前页码
        map.put("records", count);//总条数
        map.put("total", total);//总页数
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer selectArticleCount() {
        Integer count = articleDAO.selectArticleCount();
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Article selectArticleById(String id) {
        Article article = articleDAO.selectArticleById(id);
        return article;
    }

    @Override
    public List<Map> queryTermPage(String search, Integer page) throws Exception {
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
        SearchResponse searchResponse = transportClient.prepareSearch("cmfz")
                .setTypes("article")
                .setQuery(field)
                .highlighter(highlightBuilder)
                .setFrom(page)
                .setSize(2)
                .get();
        SearchHits hits1 = searchResponse.getHits();
        long totalHits = hits1.getTotalHits();
        long total = (totalHits - 1) / 5 + 1;
        SearchHit[] hits2 = hits1.getHits();
        List<Map> list = new ArrayList<Map>();
        Map<String, Object> map = new HashMap<String, Object>();
        for (SearchHit hit : hits2) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            for (String key : highlightFields.keySet()) {
                sourceAsMap.put(key, highlightFields.get(key).getFragments()[0].toString());
            }
            list.add(sourceAsMap);
        }
        map.put("total", total);
        list.add(map);
        for (Map map1 : list) {
            System.out.println(map1);
        }
        return list;
    }

    /*@Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Article> queryByHighLight(String search) {//springdate
        //高亮配置
        HighlightBuilder.Field highlightBuilder = new HighlightBuilder.Field("*");
        highlightBuilder.preTags("<span style='color:red'>")
                .postTags("</span>")
                .requireFieldMatch(false);
        //查询方式，查询源
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withIndices("cmfz")//索引
                .withTypes("article")//类型
                .withQuery(QueryBuilders.multiMatchQuery(search,"id","title","author","content","title","state","time"))//查询方式
                .withHighlightFields(highlightBuilder)
                .build();

        AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(build, Article.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                List<Article> list = new ArrayList<Article>();

                SearchHits hits = searchResponse.getHits();
                SearchHit[] searchHits = hits.getHits();
                for (SearchHit searchHit : searchHits) {
                    Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                    Article article = new Article(sourceAsMap.get("id").toString(), sourceAsMap.get("title").toString(), sourceAsMap.get("author").toString(), sourceAsMap.get("content").toString(), sourceAsMap.get("state").toString(), new Date(Long.valueOf(sourceAsMap.get("time").toString())));

                    Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                    if (highlightFields.get("id") != null) {
                        String id = highlightFields.get("id").getFragments()[0].toString();
                        article.setId(id);
                    }
                    if (highlightFields.get("title") != null) {
                        String title = highlightFields.get("title").getFragments()[0].toString();
                        article.setTitle(title);
                    }
                    if (highlightFields.get("author") != null) {
                        String author = highlightFields.get("author").getFragments()[0].toString();
                        article.setAuthor(author);
                    }
                    if (highlightFields.get("content") != null) {
                        String content = highlightFields.get("content").getFragments()[0].toString();
                        article.setContent(content);
                    }
                    if (highlightFields.get("state") != null) {
                        String state = highlightFields.get("state").getFragments()[0].toString();
                        article.setState(state);
                    }
                    if (highlightFields.get("time") != null) {
                        String time = highlightFields.get("time").getFragments()[0].toString();
                        Date date = new Date(Long.valueOf(time));
                        article.setTime(date);
                    }

                    list.add(article);

                }
                return new AggregatedPageImpl<T>((List<T>)list);
            }

            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                return null;
            }
        });

        return articles.getContent();
    }*/
}
