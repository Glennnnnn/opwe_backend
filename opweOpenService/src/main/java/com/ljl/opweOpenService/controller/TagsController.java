package com.ljl.opweOpenService.controller;

import com.ljl.opweOpenService.entity.common.ResponseResultPo;
import com.ljl.opweOpenService.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Liu Jialin
 * @Date 2024/7/21 16:09
 * @PackageName com.ljl.opweOpenService.controller
 * @ClassName TagsController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/tags")
public class TagsController {
    private TagsService tagsService;

    @Autowired
    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }


    @GetMapping("/uniqueTags")
    public ResponseResultPo queryUniqueTags() {
        ResponseResultPo responseResultPo = new ResponseResultPo();
        try {
            responseResultPo.setData(tagsService.queryUniqueTags());
            responseResultPo.setCode(HttpStatus.OK.value());
            responseResultPo.setMsg("success");
        } catch (Exception e) {
            responseResultPo.setCode(HttpStatus.BAD_REQUEST.value());
            responseResultPo.setMsg("failed");
        }
        return responseResultPo;
    }
}
