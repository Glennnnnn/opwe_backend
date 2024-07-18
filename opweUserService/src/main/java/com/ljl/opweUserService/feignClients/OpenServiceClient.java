package com.ljl.opweUserService.feignClients;

import com.ljl.opweUserService.entity.common.ResponseResultPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author Liu Jialin
 * @Date 2024/6/2 21:37
 * @PackageName com.ljl.opweSettlementService.service
 * @ClassName OpenServiceClient
 * @Description TODO
 * @Version 1.0.0
 */
@FeignClient(value = "opwe-open-service", contextId = "openServiceClient")
public interface OpenServiceClient {
    @GetMapping("/product/allProduct")
    ResponseResultPo queryAllProductFromOpenService();
}
