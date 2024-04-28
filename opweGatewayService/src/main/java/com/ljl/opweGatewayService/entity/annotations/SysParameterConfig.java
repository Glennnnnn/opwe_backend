package com.ljl.opweGatewayService.entity.annotations;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 */
@ConfigurationProperties(prefix = "oauth2.cloud.sys.parameter")
@Data
@Component
public class SysParameterConfig {
    /**
     * 白名单
     */
    private List<String> ignoreUrls;
}
