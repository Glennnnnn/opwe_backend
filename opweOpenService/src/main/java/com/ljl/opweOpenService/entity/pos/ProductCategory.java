package com.ljl.opweOpenService.entity.pos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author Liu Jialin
 * @Date 2024/9/5 14:42
 * @PackageName com.ljl.opweOpenService.entity.pos
 * @ClassName ProductCategory
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {
    private Long productCategoryId;
    private String productCategoryName;
    private Date productCategoryCreateTime;
    private Date productCategoryUpdateTime;
    private Long productCategoryCreateBy;
    private Long productCategoryUpdateBy;

}
