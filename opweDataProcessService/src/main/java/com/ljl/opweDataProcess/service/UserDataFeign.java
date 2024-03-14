package com.ljl.opweDataProcess.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author Liu Jialin
 * @Date 2024/3/11 21:26
 * @PackageName com.ljl.opweDataProcess.Service
 * @ClassName User
 * @Description TODO
 * @Version 1.0.0
 */
@FeignClient(value = "opwe-open-service")
public interface UserDataFeign {
    @GetMapping("/getUserName")
    String queryUserName();
    @GetMapping("/getUserPhone")
    String queryUserPhone();
}
