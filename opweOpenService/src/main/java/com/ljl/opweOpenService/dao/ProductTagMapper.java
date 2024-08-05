package com.ljl.opweOpenService.dao;

import com.ljl.opweOpenService.entity.pos.ProductTagPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Liu Jialin
 * @Date 2024/8/5 18:44
 * @PackageName com.ljl.opweOpenService.dao
 * @ClassName ProductTagMapper
 * @Description TODO
 * @Version 1.0.0
 */
@Mapper
public interface ProductTagMapper {
    int insertProductTag(ProductTagPo productTagPo);
}
