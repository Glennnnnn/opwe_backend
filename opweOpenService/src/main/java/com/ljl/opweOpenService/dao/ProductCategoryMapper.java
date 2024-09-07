package com.ljl.opweOpenService.dao;

import com.ljl.opweOpenService.entity.pos.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/9/5 14:58
 * @PackageName com.ljl.opweOpenService.dao
 * @ClassName ProductCategoryMapper
 * @Description TODO
 * @Version 1.0.0
 */
@Mapper
public interface ProductCategoryMapper {
    List<ProductCategory> queryProductCategoryList();
}
