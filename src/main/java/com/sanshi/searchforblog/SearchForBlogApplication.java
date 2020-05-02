package com.sanshi.searchforblog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@SpringBootApplication
public class SearchForBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchForBlogApplication.class, args);
    }
}
