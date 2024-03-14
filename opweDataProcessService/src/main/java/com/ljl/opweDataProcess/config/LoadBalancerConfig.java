package com.ljl.opweDataProcess.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @Author Liu Jialin
 * @Date 2024/3/13 22:05
 * @PackageName com.ljl.opweDataProcess.config
 * @ClassName LoadBalancerConfig
 * @Description TODO
 * @Version 1.0.0
 */
public class LoadBalancerConfig {
    //LoadBalancer just hava random and equal switch, the default strategy is equal switch, this is random
    //nacos has also a NacosLoadBalancer, this is based on the weight
    @Bean
    public ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RandomLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }
}
