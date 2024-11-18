package com.ljl.opweUserService.config;

import com.ljl.opweUserService.intercepter.FeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author Liu Jialin
 * @Date 2024/11/18 16:36
 * @PackageName com.ljl.opweUserService.config
 * @ClassName FeignConfig
 * @Description TODO
 * @Version 1.0.0
 */
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }

}
