package com.ljl.opweGatewayService.service.feignClients;

import com.alibaba.fastjson.JSONObject;
import com.ljl.opweGatewayService.entity.common.ResponseResultPo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @Author Liu Jialin
 * @Date 2024/10/24 18:43
 * @PackageName com.ljl.opweGatewayService.service.feignClients
 * @ClassName AuthServiceClient
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class AuthServiceClient {

    private final WebClient webClient;

    public AuthServiceClient(WebClient.Builder webClientBuilder) {
        // The service name registered in Eureka (auth-service)
        this.webClient = webClientBuilder.baseUrl("http://opwe-auth-service").build();
    }

    public Mono<ResponseResultPo<JSONObject>> verifyUser(String token) {
        return webClient.get()
                .uri("/authService/verifyUser")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseResultPo<JSONObject>>() {});
    }
}