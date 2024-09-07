package com.ljl.opweOpenService.controller;

import com.ljl.opweOpenService.entity.common.ResponseResultPo;
import com.ljl.opweOpenService.service.ProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Liu Jialin
 * @Date 2024/9/5 15:31
 * @PackageName com.ljl.opweOpenService.controller
 * @ClassName ProdcutCategoryController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {
    private ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService){
        this.productCategoryService = productCategoryService;
    }


    @GetMapping("/allProductCategory")
    public ResponseResultPo queryProductCategoryList(){
        ResponseResultPo responseResultPo = new ResponseResultPo();
        try {
            responseResultPo.setData(productCategoryService.queryProductCategoryList());
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg("success");
        } catch (Exception e) {
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("failed");
        }
        return responseResultPo;
    }
}
