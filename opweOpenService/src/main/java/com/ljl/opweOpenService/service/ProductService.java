package com.ljl.opweOpenService.service;

import com.ljl.opweOpenService.entity.pos.ProductPo;
import com.ljl.opweOpenService.exceptions.GeneralException;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/6/1 21:44
 * @PackageName com.ljl.opweOpenService.service
 * @ClassName ProductService
 * @Description TODO
 * @Version 1.0.0
 */
public interface ProductService {
    void insertSingleProduct(ProductPo productPo);

    List<ProductPo> queryAllProduct();

    ProductPo queryProductStockAndOnHold(Long productId);

    int processProductPreOrder(Long productId, int amount) throws GeneralException;
}
