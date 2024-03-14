package com.ljl.opweOpenService.service;

import org.springframework.web.client.RestTemplate;

/**
 * @Author Liu Jialin
 * @Date 2024/3/11 21:24
 * @PackageName com.ljl.opweOpenService.service
 * @ClassName UserService
 * @Description TODO
 * @Version 1.0.0
 */
public interface UserService {

    String checkUserName();

    String checkUserPhone();
}
