package com.ljl.opweOpenService.entity.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Liu Jialin
 * @Date 2024/7/29 20:06
 * @PackageName com.ljl.opweOpenService.entity.dtos
 * @ClassName TagValueWithId
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagValueWithIdDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tagId;
    private String tagValue;
}
