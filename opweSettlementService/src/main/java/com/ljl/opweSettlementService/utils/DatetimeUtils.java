package com.ljl.opweSettlementService.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @Author Liu Jialin
 * @Date 2024/6/4 21:55
 * @PackageName com.ljl.opweUserService.utils
 * @ClassName DatetimeUtils
 * @Description TODO
 * @Version 1.0.0
 */
@Component
public class DatetimeUtils {

    public Timestamp getCurrentTimestamp(){
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        return Timestamp.valueOf(now);
    }
}
