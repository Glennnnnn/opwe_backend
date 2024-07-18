package com.ljl.opweOpenService.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Liu Jialin
 * @Date 2024/6/18 17:32
 * @PackageName com.ljl.opweOpenService.exceptions
 * @ClassName ProductOperationException
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
