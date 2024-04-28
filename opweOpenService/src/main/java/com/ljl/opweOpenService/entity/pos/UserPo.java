package com.ljl.opweOpenService.entity.pos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author Liu Jialin
 * @Date 2023/8/12 18:38
 * @PackageName com.ljl.inventory.entity
 * @ClassName User
 * @Description TODO
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPo implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    private long userId;

    private String username;

    private String password;

    private String userRoleName;

    private Date createTime;

    private Date updateTime;

    private long createBy;

    private long updateBy;

    private int delFlag;

    private int status;



}
