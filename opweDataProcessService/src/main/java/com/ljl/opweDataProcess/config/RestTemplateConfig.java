package com.ljl.opweDataProcess.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author Liu Jialin
 * @Date 2024/3/11 21:42
 * @PackageName com.ljl.opweOpenService.config
 * @ClassName RestTemplateConfig
 * @Description TODO
 * @Version 1.0.0
 */
@Configuration
@LoadBalancerClient(value = "opwe-open-service",configuration = LoadBalancerConfig.class)
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
