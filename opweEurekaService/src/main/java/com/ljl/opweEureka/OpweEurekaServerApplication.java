package com.ljl.opweEureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author Liu Jialin
 * @Date 2024/2/27 22:11
 * @PackageName com.ljl.opweEureka
 * @ClassName OpewEurekaServerApplication
 * @Description TODO
 * @Version 1.0.0
 */
@EnableEurekaServer
@SpringBootApplication
public class OpweEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpweEurekaServerApplication.class, args);
    }
}
