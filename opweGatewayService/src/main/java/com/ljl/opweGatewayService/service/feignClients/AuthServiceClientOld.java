package com.ljl.opweGatewayService.service.feignClients;

import com.ljl.opweGatewayService.entity.common.ResponseResultPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Author Liu Jialin
 * @Date 2024/10/22 11:05
 * @PackageName com.ljl.opweGatewayService.service.feignClients
 * @ClassName AuthServiceClient
 * @Description TODO
 * @Version 1.0.0
 */
@FeignClient(value = "opwe-auth-service", contextId = "opweAuthService")
public interface AuthServiceClientOld {

    @GetMapping("/authService/verifyUser")
    ResponseResultPo<Authentication> verifyUser(@RequestHeader("Authorization") String authHeader);
}
