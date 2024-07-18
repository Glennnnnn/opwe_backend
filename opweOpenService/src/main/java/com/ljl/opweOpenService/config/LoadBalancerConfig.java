package com.ljl.opweOpenService.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author Liu Jialin
 * @Date 2024/6/4 20:14
 * @PackageName com.ljl.opweOpenService.config
 * @ClassName LoadBalancerConfig
 * @Description TODO
 * @Version 1.0.0
 */
@Configuration
@LoadBalancerClient(value = "opwe-settlement-service", configuration = RandomLoadBalancerConfig.class)
public class LoadBalancerConfig {
    @Bean
    @LoadBalanced
    public RestTemplate template(){
        return new RestTemplate();
    }
}
