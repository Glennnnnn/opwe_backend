package com.ljl.opweOpenService.entity.pos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author Liu Jialin
 * @Date 2023/9/4 19:56
 * @PackageName com.ljl.inventory.entity
 * @ClassName MenuAuthPo
 * @Description TODO
 * @Version 1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuAuthPo implements Serializable {
    private long menuAuthId;
    private String menuAuthName;
    private String menuAuthDesc;
    private String menuAuthPerms;
    private Date createTime;
    private Date updateTime;
    private long createBy;
    private long updateBy;
    private int delFlag;
    private int status;

}
