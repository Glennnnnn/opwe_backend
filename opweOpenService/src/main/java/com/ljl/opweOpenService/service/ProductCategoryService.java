package com.ljl.opweOpenService.service;

import com.ljl.opweOpenService.entity.pos.ProductCategory;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/9/5 15:28
 * @PackageName com.ljl.opweOpenService.service
 * @ClassName ProductCategoryService
 * @Description TODO
 * @Version 1.0.0
 */
public interface ProductCategoryService {
    List<ProductCategory> queryProductCategoryList();
}
