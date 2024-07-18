package com.ljl.opweUserService.service.impl;

import com.ljl.opweUserService.dao.UserMapper;
import com.ljl.opweUserService.entity.pos.UserPo;
import com.ljl.opweUserService.service.UserService;
import com.ljl.opweUserService.utils.DatetimeUtils;
import com.ljl.opweUserService.utils.SnowflakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @Author Liu Jialin
 * @Date 2024/6/4 21:41
 * @PackageName com.ljl.opweUserService.service.impl
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    SnowflakeUtil snowflakeUtil;

    @Autowired
    DatetimeUtils datetimeUtils;

    @Override
    public int insertUser(UserPo userPo) {
        Timestamp currentTime = datetimeUtils.getCurrentTimestamp();
        userPo.setUserId(snowflakeUtil.getNextId());
        userPo.setUserLoginDate(currentTime);
        userPo.setUserCreateDate(currentTime);
        userPo.setUserUpdateDate(currentTime);
        return userMapper.insertUser(userPo);
    }

    public Float confirmUserOrder(Long userId, Float orderAmount){
        Float curCredit = userMapper.queryCurUserCredit(userId);
        if(curCredit < orderAmount){
            throw new RuntimeException("order amount exceed curCredit");
        }
        Float creditAfterConfirm = curCredit - orderAmount;
        userMapper.updateUserCredit(userId, creditAfterConfirm);
        return creditAfterConfirm;
    }
}
