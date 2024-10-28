package com.ljl.opweAuthService.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljl.opweAuthService.entity.common.ResponseResultPo;
import com.ljl.opweAuthService.entity.dtos.LoginResponseDto;
import com.ljl.opweAuthService.entity.pos.UserPo;
import com.ljl.opweAuthService.service.LoginService;
import com.ljl.opweAuthService.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Liu Jialin
 * @Date 2023/8/31 19:06
 * @PackageName com.ljl.inventory.controller
 * @ClassName LoginController
 * @Description TODO
 * @Version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/authService")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseResultPo login(@RequestBody JSONObject jsonObject) {
        UserPo userPo = jsonObject.toJavaObject(UserPo.class);
        log.info(userPo.toString());
//        if (userService.checkUserByName(userPo.getUsername()).size() == 0) {
//            throw new RuntimeException("user not found");
//        }
        ResponseResultPo responseResultPo = new ResponseResultPo();
        //userPo.setUserId(userService.checkUserByName(userPo.getUsername()).get(0).getUserId());
        try{
            LoginResponseDto loginResponseDto = loginService.login(userPo);
            responseResultPo.setData(loginResponseDto);
            responseResultPo.setCode(HttpStatus.SC_OK);
            responseResultPo.setMsg("success");
        } catch (Exception e){
            responseResultPo.setCode(HttpStatus.SC_FORBIDDEN);
            responseResultPo.setMsg(e.toString());
        }
        return responseResultPo;
    }

    @GetMapping("/user/logout")
    public ResponseResultPo logout() {
        ResponseResultPo responseResultPo = loginService.logout();
        return responseResultPo;
    }

}
