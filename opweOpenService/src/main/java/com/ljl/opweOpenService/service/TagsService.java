package com.ljl.opweOpenService.service;

import com.ljl.opweOpenService.entity.dtos.TagsWithGroupDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/7/21 15:53
 * @PackageName com.ljl.opweOpenService.service.impl
 * @ClassName TagsService
 * @Description TODO
 * @Version 1.0.0
 */
public interface TagsService {
    List<TagsWithGroupDto> queryUniqueTags();
}
