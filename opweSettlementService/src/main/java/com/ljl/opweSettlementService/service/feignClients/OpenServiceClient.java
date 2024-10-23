package com.ljl.opweSettlementService.service.feignClients;

import com.ljl.opweSettlementService.entity.common.ResponseResultPo;
import com.ljl.opweSettlementService.entity.pos.ProductPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.geom.RectangularShape;
import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/6/2 21:37
 * @PackageName com.ljl.opweSettlementService.service
 * @ClassName OpenServiceClient
 * @Description TODO
 * @Version 1.0.0
 */
@FeignClient(value = "opwe-open-service", contextId = "openServiceClient")
public interface OpenServiceClient {
    @GetMapping("/product/allProduct")
    ResponseResultPo queryAllProductFromOpenService();

    @GetMapping("/product/productStockAndOnHold")
    ResponseResultPo queryProductStockAndOnHold();

    @PutMapping("/product/processProductPreOrder")
    ResponseResultPo processProductPreOrder(@RequestParam("productId") Long productId, @RequestParam("amount") Integer amount);
}
