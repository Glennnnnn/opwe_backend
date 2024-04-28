package com.ljl.opweAuthService.entity.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Liu Jialin
 * @Date 2023/8/12 18:53
 * @PackageName com.ljl.inventory.entity
 * @ClassName Result
 * @Description TODO
 * @Version 1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResultPo<T> implements Serializable {
    // 响应码
    private Integer code;
    // 提示信息
    private String msg;
    // 具体内容
    private T data;

    public ResponseResultPo(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
