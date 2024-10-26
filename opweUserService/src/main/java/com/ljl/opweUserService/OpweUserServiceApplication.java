package com.ljl.opweUserService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @Author Liu Jialin
 * @Date 2024/3/11 20:07
 * @PackageName com.ljl.opweOpenService
 * @ClassName OpweOpenServiceApplication
 * @Description TODO
 * @Version 1.0.0
 */
@SpringBootApplication
@MapperScan("com.ljl.opweUserService.dao")
@EnableFeignClients
public class OpweUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpweUserServiceApplication.class, args);
    }
}
