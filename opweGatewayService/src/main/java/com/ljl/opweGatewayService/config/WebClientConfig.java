package com.ljl.opweGatewayService.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
/**
 * @Author Liu Jialin
 * @Date 2024/10/24 18:40
 * @PackageName com.ljl.opweGatewayService.config
 * @ClassName WebClientConfig
 * @Description TODO
 * @Version 1.0.0
 */

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced  // Enables Eureka-based service discovery
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}