package com.ljl.opweAuthService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author Liu Jialin
 * @Date 2024/3/29 20:29
 * @PackageName opweAuthService
 * @ClassName OpweAuthServiceApplication
 * @Description TODO
 * @Version 1.0.0
 */
@MapperScan("com.ljl.opweAuthService.dao")
@SpringBootApplication
@EnableFeignClients
public class OpweAuthServiceApplication {
    public static void main(String[] args) {
            SpringApplication.run(OpweAuthServiceApplication.class, args);
        }
}


