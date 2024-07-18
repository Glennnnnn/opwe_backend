package com.ljl.opweOpenService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author Liu Jialin
 * @Date 2024/3/11 20:07
 * @PackageName com.ljl.opweOpenService
 * @ClassName OpweOpenServiceApplication
 * @Description TODO
 * @Version 1.0.0
 */
@SpringBootApplication
@MapperScan("com.ljl.opweOpenService.dao")
@EnableFeignClients
public class OpweOpenServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpweOpenServiceApplication.class, args);
    }
}
