package com.ljl.opweOpenService.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ljl.opweOpenService.entity.common.ResponseResultPo;
import com.ljl.opweOpenService.entity.dtos.NewProductRequestDto;
import com.ljl.opweOpenService.entity.pos.ProductPo;
import com.ljl.opweOpenService.exceptions.GeneralException;
import com.ljl.opweOpenService.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/6/1 21:24
 * @PackageName com.ljl.opweOpenService.controller
 * @ClassName ProductController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/productService/product")
public class ProductController {

    @Value("${server.port:}")
    private String port;

    @Autowired
    ProductService productService;

    @PostMapping("/basicProduct")
    public ResponseResultPo insertBasicProduct(@RequestBody JSONObject jsonObject){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            productService.insertSingleProduct(JSONObject.parseObject(jsonObject.toString(), ProductPo.class));
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg("success");
        }catch(Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("failed");

        }

        return responseResultPo;
    }

    @GetMapping("/allProduct")
    public ResponseResultPo queryAllProduct(){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            List<ProductPo> result = productService.queryAllProduct();
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg(port);
            responseResultPo.setData(result);
        }catch(Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("failed");
        }
        return responseResultPo;
    }

    @PostMapping("/newProductWithImg")
    public ResponseResultPo createNewProductWithImg(@RequestPart("productData") NewProductRequestDto productData, @RequestPart("productImg") MultipartFile productImg){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            System.out.println(productData);
//            responseResultPo.setCode(HttpStatus.OK.value());
//            responseResultPo.setMsg(port);
//            responseResultPo.setData(result);
//        }catch(GeneralException ge){
//            ge.printStackTrace();
//            responseResultPo.setCode(ge.getExceptionCode());
//            responseResultPo.setMsg(ge.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("unknown failed");
        }
        return responseResultPo;
    }

    @GetMapping("/productStockAndOnHold")
    public ResponseResultPo queryProductStockAndOnHold(@RequestParam Long productId){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            ProductPo result = productService.queryProductStockAndOnHold(productId);
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg(port);
            responseResultPo.setData(result);
        }catch(Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("failed");
        }
        return responseResultPo;
    }
    @PutMapping("/processProductPreOrder")
    public ResponseResultPo processProductPreOrder(@RequestParam Long productId, @RequestParam Integer amount){
        ResponseResultPo responseResultPo = new ResponseResultPo<>();
        try{
            int result = productService.processProductPreOrder(productId, amount);
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg(port);
            responseResultPo.setData(result);
        }catch(GeneralException ge){
            ge.printStackTrace();
            responseResultPo.setCode(ge.getExceptionCode());
            responseResultPo.setMsg(ge.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("unknown failed");
        }
        return responseResultPo;
    }

    @RabbitListener(queues = "opwe.settlement.orderQueue2")
    public void listenDirectQueue2(String msg) {
        System.out.println("Receive message from opwe.settlement.orderQueue2 ：【" + msg + "】");

    }

}
