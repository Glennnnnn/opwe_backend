package com.ljl.opweOpenService.entity.pos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Liu Jialin
 * @Date 2024/6/1 21:01
 * @PackageName com.ljl.opweOpenService.entity.pos
 * @ClassName ProductPo
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPo implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId;
    private String productName;
    private String productDesc;
    private String productImage;
    private Long productStatus;
    private Float productPrice;
    private Integer productStock;
    private Integer productOnHold;
    private Long productCategoryId;
}
