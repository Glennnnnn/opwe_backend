package com.ljl.opweOpenService.service.impl;

import com.ljl.opweOpenService.service.MsgQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @Author Liu Jialin
 * @Date 2024/5/28 20:53
 * @PackageName com.ljl.opweOpenService.service.impl
 * @ClassName MsgQueueServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Slf4j
@Service
public class MsgQueueServiceImpl implements MsgQueueService {
    @Override
    @RabbitListener(queues = "opwe.settlement.orderQueue1")
    public void receiveRawMsg(String msg) {
        log.info("opweOpenService receive msg: [" + msg +  "]");
    }

}
