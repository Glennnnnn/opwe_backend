package com.ljl.opweSettlementService.controller;

import com.ljl.opweSettlementService.entity.common.ResponseResultPo;
import com.ljl.opweSettlementService.feignClients.OpenServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Liu Jialin
 * @Date 2024/6/2 21:33
 * @PackageName com.ljl.opweSettlementService.controller
 * @ClassName OpenServiceController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/settlementService/openService")
public class OpenServiceController {

    @Autowired
    OpenServiceClient openServiceClient;

    @GetMapping("/allProductFromOpenService")
    public ResponseResultPo queryAllProduct(){
        ResponseResultPo responseResultPo = new ResponseResultPo();
        try{
            responseResultPo = openServiceClient.queryAllProductFromOpenService();
        }catch(Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("failed");
        }
        return responseResultPo;
    }

}
