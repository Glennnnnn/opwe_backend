package com.ljl.opweOpenService.dao;

import com.ljl.opweOpenService.entity.dtos.ProductResponseDto;
import com.ljl.opweOpenService.entity.pos.ProductPo;
import org.apache.ibatis.annotations.Mapper;
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
@Mapper
public interface ProductMapper {
    Integer insertSingleProduct(ProductPo productPo);

    List<ProductPo> queryAllProduct();

    ProductPo queryProductStockAndOnHold(Long productId);

    Integer queryProductOnHold(Long productId);

    Integer updateProductOnHold(@Param("productId")Long productId, @Param("productOnHold")int productOnHold);

    ProductResponseDto queryProductById(Long productId);

    List<Long> queryProductIdList(@Param("searchParams") String searchParams, @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    List<ProductResponseDto> queryProductList(@Param("productIdList") List<Long> productIdList);

    Integer countProductWithSearchParam(String searchParams);
}
