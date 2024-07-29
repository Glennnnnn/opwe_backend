package com.ljl.opweOpenService.entity.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/7/21 15:23
 * @PackageName com.ljl.opweOpenService.entity.dtos
 * @ClassName TagsWIthGroupDto
 * @Description TODO
 * @Version 1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagsWithGroupDto {
    private String tagName;
    private List<TagValueWithIdDto> tagValueList;
}
