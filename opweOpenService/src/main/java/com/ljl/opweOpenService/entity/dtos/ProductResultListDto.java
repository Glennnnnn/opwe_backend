package com.ljl.opweOpenService.entity.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/8/26 19:13
 * @PackageName com.ljl.opweOpenService.entity.dtos
 * @ClassName ProductListResultDto
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResultListDto {
    private List<ProductResponseDto> productResultList;
    private Integer count;
}
