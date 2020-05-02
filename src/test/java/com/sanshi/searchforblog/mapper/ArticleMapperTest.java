package com.sanshi.searchforblog.mapper;

import com.sanshi.searchforblog.entity.Article;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void testSelectByPrimaryKeys(){
        List<Integer> ids = Arrays.asList(1, 2, 3);
        List<Article> articles = articleMapper.selectByPrimaryKeys(ids);
        assertEquals(3, articles.size());
        assertEquals(1, articles.get(0).getId().intValue());
        assertEquals(2, articles.get(1).getId().intValue());
        assertEquals(3, articles.get(2).getId().intValue());
    }
}