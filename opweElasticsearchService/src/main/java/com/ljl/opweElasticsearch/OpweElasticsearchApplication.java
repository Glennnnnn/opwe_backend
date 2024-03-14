package com.ljl.opweElasticsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Liu Jialin
 * @Date 2024/2/26 22:10
 * @PackageName PACKAGE_NAME
 * @ClassName Application
 * @Description TODO
 * @Version 1.0.0
 */
@SpringBootApplication
@MapperScan("com.ljl.opweElasticsearch.dao")
public class OpweElasticsearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpweElasticsearchApplication.class);
    }
}
