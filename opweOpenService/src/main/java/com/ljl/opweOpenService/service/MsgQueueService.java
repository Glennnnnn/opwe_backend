package com.ljl.opweOpenService.service;

/**
 * @Author Liu Jialin
 * @Date 2024/5/28 20:53
 * @PackageName com.ljl.opweOpenService.service
 * @ClassName MsgQueueService
 * @Description TODO
 * @Version 1.0.0
 */
public interface MsgQueueService {
    void receiveRawMsg(String msg);
}
