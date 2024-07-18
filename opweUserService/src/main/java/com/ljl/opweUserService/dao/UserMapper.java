package com.ljl.opweUserService.dao;

import com.ljl.opweUserService.entity.pos.UserPo;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Liu Jialin
 * @Date 2024/6/3 20:35
 * @PackageName com.ljl.opweSettlementService.dao
 * @ClassName OrderMapper
 * @Description TODO
 * @Version 1.0.0
 */
public interface UserMapper {
    int insertUser(UserPo userPo);

    int updateUserCredit(@Param("userId") Long userId, @Param("userCredits") Float userCredits);

    Float queryCurUserCredit(Long userId);
}
