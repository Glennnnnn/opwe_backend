package com.ljl.opweOpenService.service;

import com.ljl.opweOpenService.entity.common.ResponseResultPo;
import com.ljl.opweOpenService.entity.constants.FeignClientConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author Liu Jialin
 * @Date 2024/3/29 19:44
 * @PackageName com.ljl.opweOpenService.service
 * @ClassName AuthFeignService
 * @Description TODO
 * @Version 1.0.0
 */
@Component
@FeignClient(name = FeignClientConstants.AUTH_SERVICE)
public interface AuthFeignService {
    @GetMapping(value = "/getUserName")
    ResponseResultPo getAuthUserName();
}
