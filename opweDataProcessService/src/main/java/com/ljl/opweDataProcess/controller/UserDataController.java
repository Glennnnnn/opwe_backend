package com.ljl.opweDataProcess.controller;

import com.ljl.opweDataProcess.entity.ResponseResultPo;
import com.ljl.opweDataProcess.service.UserDataFeign;
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
public class UserDataController {
    @Autowired
    UserDataFeign userDataFeign;

    @GetMapping("/queryUserName")
    public ResponseResultPo queryUserInfo(){
        ResponseResultPo responseResultPo = new ResponseResultPo();
        responseResultPo.setData(userDataFeign.queryUserName());
        responseResultPo.setCode(HttpStatus.OK.value());
        responseResultPo.setMsg("success");
        return responseResultPo;
    }

    @GetMapping("/queryUserPhone")
    public ResponseResultPo queryUserInfo2(){
        ResponseResultPo responseResultPo = new ResponseResultPo();
        responseResultPo.setData(userDataFeign.queryUserPhone());
        responseResultPo.setCode(HttpStatus.OK.value());
        responseResultPo.setMsg("success");
        return responseResultPo;
    }
}
