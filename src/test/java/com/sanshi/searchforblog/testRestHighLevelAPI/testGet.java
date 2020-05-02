package com.sanshi.searchforblog.testRestHighLevelAPI;

import com.google.gson.Gson;
import com.sanshi.searchforblog.entity.Article;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testGet {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private Gson gson;

    @Test
    public void testGetMethods() {
        GetRequest request = new GetRequest("megacorp", "employee", "1");
        try {
            GetResponse response = restHighLevelClient.get(request);
            Map<String, Object> resource = response.getSource();
            for (Map.Entry<String, Object> entry : resource.entrySet()) {
                System.out.println(entry.getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearch() {
        SearchSourceBuilder builder = new SearchSourceBuilder();

        MatchQueryBuilder matchBuilder = QueryBuilders.matchQuery("author", "数据结构");
        builder.query(matchBuilder).storedField("id");
        SearchRequest request = new SearchRequest()
                .indices("logstash-blog")
                .types("article")
                .source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request);
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits.getHits()) {
                System.out.println(hit.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIndex() {
        Article article = new Article();
        article.setTitle("我是大傻逼");
        IndexRequest request = new IndexRequest("blog", "article")
                .source(gson.toJson(article), XContentType.JSON)
                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            IndexResponse response = restHighLevelClient.index(request);
            System.out.println(response.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
