package com.ljl.opweOpenService.service.impl;

import com.ljl.opweOpenService.dao.ProductCategoryMapper;
import com.ljl.opweOpenService.entity.pos.ProductCategory;
import com.ljl.opweOpenService.service.ProductCategoryService;
import com.netflix.discovery.converters.Auto;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/9/5 15:28
 * @PackageName com.ljl.opweOpenService.service.impl
 * @ClassName ProductCategoryServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryMapper productCategoryMapper){
        this.productCategoryMapper = productCategoryMapper;
    }

    @Override
    public List<ProductCategory> queryProductCategoryList() {
        return productCategoryMapper.queryProductCategoryList();
    }
}
