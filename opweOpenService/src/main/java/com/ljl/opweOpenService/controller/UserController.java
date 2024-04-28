package com.ljl.opweOpenService.controller;

import com.ljl.opweOpenService.entity.common.ResponseResultPo;
import com.ljl.opweOpenService.service.AuthFeignService;
import com.ljl.opweOpenService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Liu Jialin
 * @Date 2024/3/11 21:28
 * @PackageName com.ljl.opweDataProcess.controller
 * @ClassName UserDataController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthFeignService authFeignService;

    @GetMapping("/getAuthUserName")
    public ResponseResultPo getUserName(){
        ResponseResultPo responseResultPo = new ResponseResultPo();
        responseResultPo.setData(authFeignService.getAuthUserName());
        responseResultPo.setCode(HttpStatus.OK.value());
        responseResultPo.setMsg("success");
        return responseResultPo;
    }

    @GetMapping("/getUserPhone")
    public ResponseResultPo getUserPhone(){
        ResponseResultPo responseResultPo = new ResponseResultPo();
        responseResultPo.setData(userService.checkUserPhone());
        responseResultPo.setCode(HttpStatus.OK.value());
        responseResultPo.setMsg("success");
        return responseResultPo;
    }
}
