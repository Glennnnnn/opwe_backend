package com.ljl.opweOpenService.service.impl;

import com.ljl.opweOpenService.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author Liu Jialin
 * @Date 2024/3/11 21:24
 * @PackageName com.ljl.opweOpenService.service.impl
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Value("${server.port}")
    private String port;

    @Override
    public String checkUserName() {
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject("http://opweOpenService/" + "queryUser", String.class);
        return "userName" + port;
    }

    @Override
    public String checkUserPhone() {
        return "userPhone" + port;
    }
}
