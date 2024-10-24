package com.ljl.opweAuthService.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljl.opweAuthService.entity.common.ResponseResultPo;
import org.apache.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Liu Jialin
 * @Date 2024/10/24 13:38
 * @PackageName com.ljl.opweAuthService.controller
 * @ClassName AuthController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/authService")
public class AuthController {
    @GetMapping("/verifyUser")
    public ResponseResultPo verifyUser(@RequestHeader("Authorization") String authHeader) {
        ResponseResultPo responseResultPo = new ResponseResultPo();
        try {
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
//                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                responseResultPo.setCode(HttpStatus.SC_OK);
                responseResultPo.setData(JSONObject.toJSON(authentication));
                // String username = userDetails.getUsername();
                // retrieve additional information from userDetails if needed

            }
        }catch (Exception e){
            responseResultPo.setCode(HttpStatus.SC_FORBIDDEN);
            responseResultPo.setMsg("failed");

        }
        return responseResultPo;
    }

}
