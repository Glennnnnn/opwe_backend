package com.ljl.opweAuthService.entity.pos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/4/9 21:17
 * @PackageName com.ljl.opweAuthService.entity.pos
 * @ClassName UserPo
 * @Description TODO
 * @Version 1.0.0
 */
@Data
public class UserPo implements Serializable {
    private Long userId;

    private String userName;

    private String userPassword;

    private String userEmail;

    private String userPhone;

    private String userRoleName;

    private Integer userStatus;

    private Date createTime;

    private Date updateTime;

    private long createBy;

    private long updateBy;

    private static final long serialVersionUID = 1L;
}
