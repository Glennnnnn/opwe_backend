package com.ljl.opweUserService.config;

import com.ljl.opweUserService.utils.SnowflakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Liu Jialin
 * @Date 2023/12/1 15:07
 * @PackageName com.ljl.ivtData.config
 * @ClassName SnowFlakeConfig
 * @Description TODO
 * @Version 1.0.0
 */
@Slf4j
@Configuration
public class SnowFlakeConfig {
    @Value("${SnowFlake.computerRoomId}")
    private long computerRoomId;

    @Value("${SnowFlake.machineId}")
    private long machineId;

    @Bean
    public SnowflakeUtil snowflakeUtil() {
        return new SnowflakeUtil(computerRoomId,machineId);
    }
}
