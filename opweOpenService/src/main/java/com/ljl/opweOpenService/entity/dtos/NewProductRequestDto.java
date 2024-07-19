package com.ljl.opweOpenService.entity.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

/**
 * @Author Liu Jialin
 * @Date 2024/7/19 14:12
 * @PackageName com.ljl.opweOpenService.entity.dtos
 * @ClassName NewProductRequestDto
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewProductRequestDto {
    private String productName;
    private String productDesc;
    private Float productPrice;
    private Integer productStock;
    private Integer productOnHold;
}
