package com.ljl.opweOpenService.service.impl;

import com.ljl.opweOpenService.dao.TagMapper;
import com.ljl.opweOpenService.entity.dtos.TagsWithGroupDto;
import com.ljl.opweOpenService.service.TagsService;
import com.netflix.servo.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/7/21 15:54
 * @PackageName com.ljl.opweOpenService.service.impl
 * @ClassName TagsServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class TagsServiceImpl implements TagsService {
    private final TagMapper tagMapper;

    @Autowired
    public TagsServiceImpl(TagMapper tagMapper){
        this.tagMapper = tagMapper;
    }


    @Override
    public List<TagsWithGroupDto> queryUniqueTags() {
        return tagMapper.queryUniqueTags();
    }
}
