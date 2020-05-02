package com.sanshi.searchforblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Integer id;

    private String title;

    private String author;

    private Date createTime;

    private Date updateTime;

    private String content;
}