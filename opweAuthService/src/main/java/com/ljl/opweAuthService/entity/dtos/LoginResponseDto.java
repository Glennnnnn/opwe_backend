package com.ljl.opweAuthService.entity.dtos;

import com.ljl.opweAuthService.entity.pos.UserPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Liu Jialin
 * @Date 2024/10/28 20:11
 * @PackageName com.ljl.opweAuthService.entity.dtos
 * @ClassName LoginResponseDto
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private LoginUserResDto loginUserResDto;
    private String token;
}
