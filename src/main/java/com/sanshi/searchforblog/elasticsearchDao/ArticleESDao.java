package com.sanshi.searchforblog.elasticsearchDao;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ArticleESDao {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 根据字段查询内容相关性.
     * @param words
     * @return 返回id列表
     */
    public List<Integer> getIdsByWords(String words) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.multiMatchQuery(words, "title", "author", "content"));
        builder.storedField("id");
        builder.from(0);
        builder.size(10);
        SearchRequest request = new SearchRequest()
                .indices("logstash-blog")
                .types("article")
                .source(builder);
        List<Integer> ids = new ArrayList<>();
        try {
            SearchResponse response = restHighLevelClient.search(request);
            for (SearchHit hit : response.getHits()) {
                ids.add(Integer.valueOf(hit.getId()));
            }
            return ids;
        } catch (Exception e) {
            log.info("elastic search {} errors, {}", words, e);
            return null;
        }
    }

    /**
     * 根据字段查询标题相关性
     * @param words
     * @return 返回id列表，用于数据库查询
     */
    public List<Integer> getIdsByTitle(String words) {
        return null;
    }

    /**
     * 根据字段查询作者相关性
     * @param words
     * @return 返回id列表，用于数据库查询
     */
    public List<Integer> getIdsByAuthor(String words) {
        return null;
    }
}
