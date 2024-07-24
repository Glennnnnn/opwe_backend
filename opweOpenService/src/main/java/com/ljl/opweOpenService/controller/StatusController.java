package com.ljl.opweOpenService.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ljl.opweOpenService.entity.common.ResponseResultPo;
import com.ljl.opweOpenService.entity.pos.ProductPo;
import com.ljl.opweOpenService.entity.pos.StatusPo;
import com.ljl.opweOpenService.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/7/24 18:55
 * @PackageName com.ljl.opweOpenService.controller
 * @ClassName StatusController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/status")
public class StatusController {
    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService){
        this.statusService = statusService;
    }

    @GetMapping("/statusByGroup")
    public ResponseResultPo queryStatusByState(@RequestParam String statusGroup){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            List<StatusPo> result =  statusService.queryStatusByGroup(statusGroup);
            responseResultPo.setData(result);
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg("success");
        }catch(Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("failed");

        }

        return responseResultPo;
    }
}
