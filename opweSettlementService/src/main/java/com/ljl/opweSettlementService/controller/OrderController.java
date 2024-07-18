package com.ljl.opweSettlementService.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ljl.opweSettlementService.entity.common.ResponseResultPo;
import com.ljl.opweSettlementService.entity.pos.OrderPo;
import com.ljl.opweSettlementService.service.OrderService;
import com.ljl.opweSettlementService.utils.SnowflakeUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Liu Jialin
 * @Date 2024/6/4 20:46
 * @PackageName com.ljl.opweOpenService.controller
 * @ClassName OrderController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/settlementService/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    String CONFIRM_EXCHANGE_NAME = "opwe.settlement.direct";

    @PostMapping("/newOrder")
    public ResponseResultPo insertNewOrder(@RequestBody JSONObject jsonObject){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            orderService.insertNewOrder(JSONObject.parseObject(jsonObject.toString(), OrderPo.class));
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg("success");
        }catch(Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("failed");

        }
        return responseResultPo;
    }

    @PutMapping("/confirmOrder")
    public ResponseResultPo confirmOrder(@RequestBody JSONObject jsonObject){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            Long orderId = jsonObject.containsKey("orderId")? jsonObject.getLong("orderId") : null;
            orderService.confirmOrder(orderId);
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg("success");
        }catch(Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("failed");

        }
        return responseResultPo;
    }

    @RabbitListener(queues = "opwe.settlement.orderStatusCheckQueue1")
    public void listenDirectQueue1(String msg) {
        System.out.println("消费者1接收到opwe.settlement.orderQueue1的消息：【" + msg + "】");
        System.out.println("check the status is confirmed");

//        rabbitTemplate.convertAndSend(CONFIRM_EXCHANGE_NAME, "orderConfirm", "confirm of:" + msg.split(":")[1]);
    }
}
