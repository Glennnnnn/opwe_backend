package com.ljl.opweSettlementService.entity.pos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Author Liu Jialin
 * @Date 2024/6/3 20:29
 * @PackageName com.ljl.opweSettlementService.entity.pos
 * @ClassName OrderPo
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPo {
    private Long orderId;
    private String orderName;
    private String orderDesc;
    private Integer orderStatus;
    private Timestamp orderCreateTime;
    private Timestamp orderUpdateTime;
    private Long orderCreateBy;
    private Long orderUpdateBy;
    private Integer orderAmount;
    private Long productId;
    private Float orderPrice;
    private Long userId;
}
