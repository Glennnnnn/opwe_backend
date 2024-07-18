package com.ljl.opweSettlementService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@MapperScan("com.ljl.opweSettlementService.dao")
@EnableFeignClients
public class OpweSettlementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpweSettlementServiceApplication.class, args);
    }
}
