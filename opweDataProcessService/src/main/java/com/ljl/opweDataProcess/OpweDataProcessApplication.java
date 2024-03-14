package com.ljl.opweDataProcess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author Liu Jialin
 * @Date 2024/2/27 22:35
 * @PackageName com.ljl.opweDataProcess
 * @ClassName OpweDataProcessApplication
 * @Description TODO
 * @Version 1.0.0
 */
@SpringBootApplication
@EnableFeignClients
public class OpweDataProcessApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpweDataProcessApplication.class, args);
    }
}
