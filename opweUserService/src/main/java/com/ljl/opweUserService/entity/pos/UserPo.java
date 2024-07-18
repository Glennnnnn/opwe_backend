package com.ljl.opweUserService.entity.pos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Author Liu Jialin
 * @Date 2024/6/4 21:25
 * @PackageName com.ljl.opweUserService.entity.pos
 * @ClassName UserPo
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPo {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private Timestamp userCreateDate;
    private Timestamp userUpdateDate;
    private Timestamp userLoginDate;
    private Float userCredits;
}
