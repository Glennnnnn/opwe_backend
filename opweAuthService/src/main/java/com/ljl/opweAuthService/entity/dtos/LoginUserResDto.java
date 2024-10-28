package com.ljl.opweAuthService.entity.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Liu Jialin
 * @Date 2024/10/28 20:15
 * @PackageName com.ljl.opweAuthService.entity.dtos
 * @ClassName LoginUserResDto
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserResDto {
    private Long userId;

    private String userName;

    private String userEmail;

    private String userPhone;

    private String userRoleName;
}
