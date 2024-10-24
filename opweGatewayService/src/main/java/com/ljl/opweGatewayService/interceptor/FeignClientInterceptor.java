package com.ljl.opweGatewayService.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @Author Liu Jialin
 * @Date 2024/10/24 14:42
 * @PackageName com.ljl.opweUserService.config
 * @ClassName FeignClientConfig
 * @Description TODO
 * @Version 1.0.0
 */
@Component
public class FeignClientInterceptor implements RequestInterceptor {

    public void apply(RequestTemplate requestTemplate) {
        Mono.deferContextual(context -> {
            if (context.hasKey("authToken")) {
                String token = context.get("authToken");
                System.out.println("token" + token);;
                requestTemplate.header("Authorization", "Bearer " + token);  // Add token to Feign request header
            }
            return Mono.empty();
        }).subscribeOn(Schedulers.boundedElastic()).block();
    }
//
//    private String getTokenFromContext() {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (requestAttributes != null) {
//            String token = (String) requestAttributes.getAttribute("Authorization", RequestAttributes.SCOPE_REQUEST);
//            return token;
//        }
//        return null;
//    }
}
