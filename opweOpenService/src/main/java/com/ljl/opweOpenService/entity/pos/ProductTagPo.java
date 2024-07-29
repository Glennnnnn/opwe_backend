package com.ljl.opweOpenService.entity.pos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Liu Jialin
 * @Date 2024/7/29 19:21
 * @PackageName com.ljl.opweOpenService.entity.pos
 * @ClassName ProductTagPo
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTagPo {
    private Long productTagId;
    private Long productId;
    private Long tagId;
}
