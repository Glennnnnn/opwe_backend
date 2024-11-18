package com.ljl.opweUserService.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljl.opweUserService.dao.UserMapper;
import com.ljl.opweUserService.entity.common.ResponseResultPo;
import com.ljl.opweUserService.entity.pos.ProductPo;
import com.ljl.opweUserService.entity.pos.UserPo;
import com.ljl.opweUserService.service.UserService;
import com.ljl.opweUserService.service.feignClients.OpenServiceClient;
import com.ljl.opweUserService.utils.DatetimeUtils;
import com.ljl.opweUserService.utils.SnowflakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

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

    @Autowired
    OpenServiceClient openServiceClient;


    private final ObjectMapper objectMapper = new ObjectMapper();

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

    public Float queryUserCredit(Long userId){
        return userMapper.queryCurUserCredit(userId);
    }

    @Override
    public List<ProductPo> testRequestToOpenService() {
        List<ProductPo> result = getDataListFromFeign(openServiceClient.queryAllProductFromOpenService(), ProductPo.class);
        return result;
        //return objectMapper.convertValue(openServiceClient.queryAllProductFromOpenService().getData(), new TypeReference<List<ProductPo>>() {});
    }

    public <T> List<T> getDataListFromFeign(ResponseResultPo<?> response, Class<T> clazz) {
        // Use TypeReference to specify a list of the desired class

        return objectMapper.convertValue(response.getData(), new TypeReference<List<T>>() {});
    }


}
