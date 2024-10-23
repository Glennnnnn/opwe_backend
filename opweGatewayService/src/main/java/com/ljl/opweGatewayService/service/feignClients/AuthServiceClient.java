package com.ljl.opweGatewayService.service.feignClients;

import com.ljl.opweGatewayService.entity.common.ResponseResultPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

/**
 * @Author Liu Jialin
 * @Date 2024/10/22 11:05
 * @PackageName com.ljl.opweGatewayService.service.feignClients
 * @ClassName AuthServiceClient
 * @Description TODO
 * @Version 1.0.0
 */
@FeignClient(value = "opwe-auth-service", contextId = "opweAuthService")
public interface AuthServiceClient {

    @GetMapping("/user/loadUserByUsername")
    Mono<ResponseResultPo<UserDetails>> loadUserByUsername(@RequestParam("userName") String userName);
}
