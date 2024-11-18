package com.ljl.opweUserService.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ljl.opweUserService.entity.common.ResponseResultPo;
import com.ljl.opweUserService.entity.pos.UserPo;
import com.ljl.opweUserService.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Liu Jialin
 * @Date 2024/6/2 21:33
 * @PackageName com.ljl.opweSettlementService.controller
 * @ClassName OpenServiceController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/userService/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/basicUser")
    public ResponseResultPo insertUser(@RequestBody JSONObject jsonObject){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            userService.insertUser(JSONObject.parseObject(jsonObject.toString(), UserPo.class));
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg("success");
        }catch(Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("failed");

        }
        return responseResultPo;
    }

    @GetMapping("/queryUserCredit")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RolesAllowed("ADMIN")
//    @Secured("ROLE_ADMIN")
    public ResponseResultPo queryUserCredit(@RequestParam Long userId){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            responseResultPo.setData(userService.queryUserCredit(userId));
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg("success");
        }catch(Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("failed");
        }
        return responseResultPo;
    }

    @GetMapping("/testRequestToOpenService")
    public ResponseResultPo testRequestToOpenService(){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            responseResultPo.setData(userService.testRequestToOpenService());
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg("success");
        }catch(Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("failed");
        }
        return responseResultPo;
    }

    @RabbitListener(queues = "opwe.settlement.orderQueue1")
    public void listenDirectQueue1(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        userService.confirmUserOrder(jsonObject.getLong("userId"), jsonObject.getFloatValue("orderAmount"));
        System.out.println("消费者1接收到opwe.settlement.orderQueue1的消息：【" + msg + "】");
    }



}
