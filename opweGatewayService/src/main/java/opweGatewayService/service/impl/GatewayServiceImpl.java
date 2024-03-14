package opweGatewayService.service.impl;

import opweGatewayService.service.GatewayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * @Author Liu Jialin
 * @Date 2024/3/14 22:37
 * @PackageName opweGatewayService.service.impl
 * @ClassName GatewayService
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class GatewayServiceImpl implements GatewayService {
    @Value("${server.port}")
    private String serverPort;

    @Override
    public String routeToOpenService() {
        return "openServe";
    }
}
