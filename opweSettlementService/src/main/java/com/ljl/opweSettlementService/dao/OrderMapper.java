package com.ljl.opweSettlementService.dao;

import com.ljl.opweSettlementService.entity.pos.OrderPo;

/**
 * @Author Liu Jialin
 * @Date 2024/6/3 20:35
 * @PackageName com.ljl.opweSettlementService.dao
 * @ClassName OrderMapper
 * @Description TODO
 * @Version 1.0.0
 */
public interface OrderMapper {
    int insertNewOrder(OrderPo orderPo);

    OrderPo queryOrderDataWithId(Long orderId);
}
