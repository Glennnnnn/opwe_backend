package com.ljl.opweSettlementService.service;

import com.ljl.opweSettlementService.entity.pos.OrderPo;
import com.ljl.opweSettlementService.exceptions.GeneralException;

/**
 * @Author Liu Jialin
 * @Date 2024/6/5 21:48
 * @PackageName com.ljl.opweSettlementService.service
 * @ClassName OrderService
 * @Description TODO
 * @Version 1.0.0
 */
public interface OrderService {
    boolean insertNewOrder(OrderPo orderPo) throws GeneralException;

    void confirmOrder(Long orderId);
}
