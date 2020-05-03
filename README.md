# Getting Started

### 描述

基于Elastic Search的简单博客搜索系统，仅实现简单的新增博客，搜索博客api

### 环境

windows10

elastic search 6.0.0

mysql 5.7.26

logstash 6.0.0

### logstash配置
logstash-mysql.conf

```
input {
  jdbc {
    jdbc_driver_library => "D:\ELK\logstash-6.0.0\mysql-connector-java-8.0.19.jar"
    jdbc_driver_class => "com.mysql.cj.jdbc.Driver"
    jdbc_connection_string => "jdbc:mysql://localhost:3306/es_blog?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC"
    jdbc_user => "root"
    jdbc_password => "1234567890"
    #是否记录上一次的值
    record_last_run => true
    #使用指定的追踪字段追踪，不设置的话会使用时间追踪
    use_column_value => true
    #追踪的字段
    tracking_column => id
    #存放上一个sql_last_value的值，需要在文件中初始化指定字段值
    last_run_metadata_path => "D:\ELK\logstash-6.0.0\config\station_parameter.txt"
    clean_run => false
    statement => "SELECT id, title, author, content FROM t_article where id > :sql_last_value"
    #开启分页查询
    jdbc_paging_enabled => "true"
    jdbc_page_size => "50000"
    #调度时间"分 时 天 月 年"，默认为一分钟
    schedule => "* * * * *"
    #设置时区
    jdbc_default_timezone =>"Asia/Shanghai"
  }
}

filter {
   json {
        source => "message"
        remove_field => ["message"]
    }
}

output {
  stdout {
    codec => rubydebug
  }
  elasticsearch {
    hosts => "127.0.0.1:9200"
    index => "logstash-blog"
    document_id => "%{id}"
    document_type => "article"
  }        
} 
```

### 参考文档

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [MyBatis Framework](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#using-boot-devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [MyBatis Quick Start](https://github.com/mybatis/spring-boot-starter/wiki/Quick-Start)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

