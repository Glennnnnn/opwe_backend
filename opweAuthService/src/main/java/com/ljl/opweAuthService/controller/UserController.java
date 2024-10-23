package com.ljl.opweAuthService.controller;

import com.ljl.opweAuthService.entity.common.ResponseResultPo;
import com.ljl.opweAuthService.entity.pos.AuthUserPo;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Liu Jialin
 * @Date 2024/10/22 11:45
 * @PackageName com.ljl.opweAuthService.controller
 * @ClassName UserController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private UserDetailsService userDetailsService;

    @Autowired
    public UserController(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }
    @GetMapping("/loadUserByUsername")
    public ResponseResultPo loadUserByUsername(@RequestParam String userName){
        ResponseResultPo responseResultPo = new ResponseResultPo();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        responseResultPo.setData(userDetails);
        responseResultPo.setCode(HttpStatus.SC_OK);
        responseResultPo.setMsg("Success");
        return responseResultPo;
    }
}
