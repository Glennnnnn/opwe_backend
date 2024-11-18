package com.ljl.opweUserService.intercepter;

import com.ljl.opweUserService.manager.FeignThreadLocalManager;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author Liu Jialin
 * @Date 2024/11/18 13:18
 * @PackageName com.ljl.opweUserService.intercepter
 * @ClassName FeignInterceptor
 * @Description TODO
 * @Version 1.0.0
 */

public class FeignInterceptor implements RequestInterceptor{
    @Override
    public void apply(RequestTemplate requestTemplate) {
            // Add custom logic to modify request
            requestTemplate.header("X-Custom-Auth", FeignThreadLocalManager.getAuthorizationHeader());
    }
}
