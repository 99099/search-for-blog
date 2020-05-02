package com.sanshi.searchforblog.controller;

import com.sanshi.searchforblog.entity.Article;
import com.sanshi.searchforblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 添加文章接口
     * @return 添加文章的完整对象
     */
    @PostMapping("/article/add")
    public Article addArticle(@RequestBody Article article) {
        Article complete = articleService.addArticle(article);
        if (complete == null) {
            return null;
        }
        return complete;
    }


    @GetMapping("/article/search/{words}")
    public List<Article> searchArticle(@PathVariable("words") String words) {
        List<Article> articles = articleService.searchArticle(words);
        if (articles == null) {
            return null;
        }
        return articles;
    }
}
