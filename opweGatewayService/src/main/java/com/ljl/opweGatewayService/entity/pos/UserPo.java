package com.ljl.opweGatewayService.entity.pos;

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

    private String userName;

    private String userPhone;

    private String userEmail;

    private String userPassword;

    private Long userRole;

    private int userStatus;

    private Date createTime;

    private Date updateTime;

}
