package com.ljl.opweAuthService.dao;

import com.ljl.opweAuthService.entity.pos.UserPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2023/8/12 19:00
 * @PackageName com.ljl.inventory.dao
 * @ClassName UserMapper
 * @Description TODO
 * @Version 1.0.0
 */

//@Repository
@Mapper
public interface UserAuthMapper {
    List<UserPo> checkUserByName(String userName);

    List<UserPo> queryBasicUserInfoByName(String userName);

    List<UserPo> queryBasicUserInfoById(Long userId);

    List<String> queryUserRoleByUsername(String userName);


}
