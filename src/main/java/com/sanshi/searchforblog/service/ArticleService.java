package com.sanshi.searchforblog.service;

import com.sanshi.searchforblog.elasticsearchDao.ArticleESDao;
import com.sanshi.searchforblog.entity.Article;
import com.sanshi.searchforblog.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleESDao articleESDao;

    /**
     * 新增文章
     * @param article
     * @return 文章的id
     */
    public Article addArticle(Article article) {
        if (article.getTitle() == null) {
            article.setTitle("");
        }
        if (article.getAuthor() == null) {
            article.setAuthor("");
        }
        if (article.getContent() == null) {
            article.setContent("");
        }
        int id = articleMapper.insert(article);
        if (id <= 0) {
            return null;
        }
        Article complete = articleMapper.selectByPrimaryKey(article.getId());
        log.info("insert article table values {}", complete);
        return complete;
    }

    /**
     * 根据字段搜索文章，使用es
     * @param words
     * @return 文章列表
     */
    public List<Article> searchArticle(String words) {
        List<Article> res = new ArrayList<>();
        if (words == null || "".equals(words)) {
            return res;
        }
        List<Integer> ids = articleESDao.getIdsByWords(words);
        if (ids == null) {
            return null;
        }
        if (ids.isEmpty()) {
            return res;
        }
        res = articleMapper.selectByPrimaryKeys(ids);
        log.info("elastic search {}, get {}", words, res);
        return res;
    }
}
