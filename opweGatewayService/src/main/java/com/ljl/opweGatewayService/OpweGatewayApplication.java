package com.ljl.opweGatewayService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Liu Jialin
 * @Date 2024/3/14 22:02
 * @PackageName opweGatewayService
 * @ClassName OpweGatewayApplication
 * @Description TODO
 * @Version 1.0.0
 */
@MapperScan("com.ljl.opweGatewayService.dao")
@SpringBootApplication
public class OpweGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpweGatewayApplication.class, args);
    }
}
