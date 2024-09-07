package com.ljl.opweOpenService.entity.dtos;

import com.ljl.opweOpenService.entity.pos.TagPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/8/5 14:28
 * @PackageName com.ljl.opweOpenService.entity.dtos
 * @ClassName ProductResponseDto
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private Long productId;
    private String productName;
    private String productDesc;
    private String productImageRoute;
    private String productImage;
    private String productStatus;
    private String productPrice;
    private Integer productStock;
    private String productOnHold;
    private String productCategoryName;
    private List<TagPo> productTags;
}
