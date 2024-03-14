package com.ljl.opweOpenService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Liu Jialin
 * @Date 2024/3/11 20:07
 * @PackageName com.ljl.opweOpenService
 * @ClassName OpweOpenServiceApplication
 * @Description TODO
 * @Version 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OpweOpenServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpweOpenServiceApplication.class, args);
    }
}
