package com.ljl.opweSettlementService.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Liu Jialin
 * @Date 2024/6/18 22:19
 * @PackageName com.ljl.opweSettlementService.exceptions
 * @ClassName GeneralException
 * @Description TODO
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralException extends Exception{
    Integer exceptionCode;
    Integer operatorCode;
    String message;
}
