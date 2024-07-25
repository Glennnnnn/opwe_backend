package com.ljl.opweOpenService.entity.pos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Liu Jialin
 * @Date 2024/7/24 18:33
 * @PackageName com.ljl.opweOpenService.entity.pos
 * @ClassName StatusPo
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusPo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long statusId;
    private String statusName;
    private String statusGroup;
}
