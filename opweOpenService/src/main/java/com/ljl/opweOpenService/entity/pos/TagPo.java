package com.ljl.opweOpenService.entity.pos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Liu Jialin
 * @Date 2024/7/21 14:34
 * @PackageName com.ljl.opweOpenService.entity.pos
 * @ClassName TagPo
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagPo {
    private Long tagId;
    private String tagName;
    private String tagValue;
    private String tagGroup;
    private String tagDesc;
}
