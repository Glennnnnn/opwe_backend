package com.ljl.opweOpenService.dao;

import com.ljl.opweOpenService.entity.dtos.TagsWithGroupDto;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/7/21 14:53
 * @PackageName com.ljl.opweOpenService.dao
 * @ClassName TagMapper
 * @Description TODO
 * @Version 1.0.0
 */
public interface TagMapper {
    List<TagsWithGroupDto> queryUniqueTags();
}
