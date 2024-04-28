package com.ljl.opweOpenService.dao;

import com.ljl.opweOpenService.entity.pos.UserPo;
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
public interface UserMapper {
    List<UserPo> checkUserByName(String username);

    List<UserPo> queryBasicUserInfoByName(String name);

    List<UserPo> queryBasicUserInfoById(Long userId);
}
