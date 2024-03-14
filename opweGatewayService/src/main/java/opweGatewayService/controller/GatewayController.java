package opweGatewayService.controller;

import opweGatewayService.entity.ResponseResultPo;
import opweGatewayService.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Liu Jialin
 * @Date 2024/3/14 22:34
 * @PackageName opweGatewayService.controller
 * @ClassName GatewayController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
public class GatewayController {
    @Autowired
    GatewayService gatewayService;

    @GetMapping("routeToOpenService")
    public ResponseResultPo routeToOpenService(){
        ResponseResultPo responseResultPo = new ResponseResultPo();
        responseResultPo.setData(gatewayService.routeToOpenService());
        return responseResultPo;
    };
}
