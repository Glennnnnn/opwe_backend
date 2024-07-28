package com.ljl.opweOpenService.entity.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Liu Jialin
 * @Date 2024/7/28 18:49
 * @PackageName com.ljl.opweOpenService.entity.dtos
 * @ClassName ProductWithTagsDto
 * @Description TODO
 * @Version 1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithImgDto {
    private String productName;
    private String productDesc;
    private MultipartFile productImage;
    private Long productStatus;
    private Float productPrice;
    private Integer productStock;
    private Integer productOnHold;
}
