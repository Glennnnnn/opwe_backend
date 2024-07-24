package com.ljl.opweOpenService.entity.enums;

/**
 * @Author Liu Jialin
 * @Date 2024/7/24 19:37
 * @PackageName com.ljl.opweOpenService.entity.enums
 * @ClassName StatusEnum
 * @Description TODO
 * @Version 1.0.0
 */
public enum StatusGroupEnum {
    ProductGroupStatus("product.status");

    private final String statusGroup;

    StatusGroupEnum(String statusGroup){
        this.statusGroup = statusGroup;
    }

    public String getStatusGroup(){
        return statusGroup;
    }
}
