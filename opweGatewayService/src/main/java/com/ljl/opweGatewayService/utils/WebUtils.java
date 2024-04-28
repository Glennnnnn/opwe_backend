package com.ljl.opweGatewayService.utils;


import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @Author Liu Jialin
 * @Date 2023/9/1 21:04
 * @PackageName com.ljl.inventory.utils
 * @ClassName WebUtils
 * @Description TODO
 * @Version 1.0.0
 */
public class WebUtils {
    public static ServerHttpResponse renderString(ServerWebExchange exchange, String msg, int status) {
        ServerHttpResponse response = null;
        try {
            response = exchange.getResponse();
            response.setRawStatusCode(status);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            response.getHeaders().set("charset", "utf-8");
            DataBuffer buffer = response.bufferFactory().wrap(msg.getBytes());
            response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
