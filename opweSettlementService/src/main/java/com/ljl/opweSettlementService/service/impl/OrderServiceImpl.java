package com.ljl.opweSettlementService.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.ljl.opweSettlementService.dao.OrderMapper;
import com.ljl.opweSettlementService.entity.common.ResponseResultPo;
import com.ljl.opweSettlementService.entity.pos.OrderPo;
import com.ljl.opweSettlementService.entity.pos.ProductPo;
import com.ljl.opweSettlementService.exceptions.GeneralException;
import com.ljl.opweSettlementService.service.feignClients.OpenServiceClient;
import com.ljl.opweSettlementService.service.OrderService;
import com.ljl.opweSettlementService.utils.DatetimeUtils;
import com.ljl.opweSettlementService.utils.SnowflakeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @Author Liu Jialin
 * @Date 2024/6/5 22:24
 * @PackageName com.ljl.opweSettlementService.service.impl
 * @ClassName OrderServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    SnowflakeUtil snowflakeUtil;

    @Autowired
    DatetimeUtils datetimeUtils;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    OpenServiceClient openServiceClient;

    String CONFIRM_EXCHANGE_NAME = "opwe.settlement.direct";

    String pending_EXCHANGE_NAME = "opwe.settlement.orderStatusCheck";

    @Override
    public boolean insertNewOrder(OrderPo orderPo) throws GeneralException {
        ProductPo productPo = (ProductPo)openServiceClient.queryProductStockAndOnHold().getData();
        if (orderPo.getOrderAmount() <= productPo.getProductStock() - productPo.getProductOnHold()){
            Timestamp currentTime = datetimeUtils.getCurrentTimestamp();
            orderPo.setOrderId(snowflakeUtil.getNextId());
            orderPo.setOrderCreateTime(currentTime);
            orderPo.setOrderUpdateTime(currentTime);
            ResponseResultPo responseResultPo = openServiceClient.processProductPreOrder(orderPo.getProductId(), orderPo.getOrderAmount());
            if (responseResultPo.getCode() != HttpStatus.OK.value()){
                throw new GeneralException(responseResultPo.getCode(), null, responseResultPo.getMsg());
            }
            orderMapper.insertNewOrder(orderPo);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productId", orderPo.getProductId());
            jsonObject.put("amount", orderPo.getOrderAmount());
            rabbitTemplate.convertAndSend(pending_EXCHANGE_NAME, "orderStatusCheck", jsonObject, message -> {
                // 添加延迟消息属性
                message.getMessageProperties().setDelay(5000);
                return message;
            });
            return true;
        }else{
            throw new RuntimeException("amount exceed limit!");
        }


    }

    @Override
    public void confirmOrder(Long orderId) {
        //TODO change object to a json object
        OrderPo orderPo = orderMapper.queryOrderDataWithId(orderId);
        if(null == orderPo){
            throw new RuntimeException("no such order");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId", orderPo.getProductId());
        jsonObject.put("userId", orderPo.getUserId());
        jsonObject.put("orderAmount", orderPo.getOrderAmount());
        rabbitTemplate.convertAndSend(CONFIRM_EXCHANGE_NAME, "orderConfirm", jsonObject);
    }
}
