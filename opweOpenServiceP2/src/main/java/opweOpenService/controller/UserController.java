package opweOpenService.controller;

import opweOpenService.entity.ResponseResultPo;
import opweOpenService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Liu Jialin
 * @Date 2024/3/11 21:28
 * @PackageName com.ljl.opweDataProcess.controller
 * @ClassName UserDataController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getUserName")
    public ResponseResultPo getUserName(){
        ResponseResultPo responseResultPo = new ResponseResultPo();
        responseResultPo.setData(userService.checkUserName());
        responseResultPo.setCode(HttpStatus.OK.value());
        responseResultPo.setMsg("success");
        return responseResultPo;
    }

    @GetMapping("/getUserPhone")
    public ResponseResultPo getUserPhone(){
        ResponseResultPo responseResultPo = new ResponseResultPo();
        responseResultPo.setData(userService.checkUserPhone());
        responseResultPo.setCode(HttpStatus.OK.value());
        responseResultPo.setMsg("success");
        return responseResultPo;
    }
}
