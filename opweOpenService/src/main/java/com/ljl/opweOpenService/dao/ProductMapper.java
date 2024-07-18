package com.ljl.opweOpenService.dao;

import com.ljl.opweOpenService.entity.pos.ProductPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/6/1 21:15
 * @PackageName com.ljl.opweOpenService.dao
 * @ClassName ProductMapper
 * @Description TODO
 * @Version 1.0.0
 */

public interface ProductMapper {
    int insertSingleProduct(ProductPo productPo);

    List<ProductPo> queryAllProduct();

    ProductPo queryProductStockAndOnHold(Long productId);

    int queryProductOnHold(Long productId);

    int updateProductOnHold(@Param("productId")Long productId, @Param("productOnHold")int productOnHold);
}
