package com.ljl.opweGatewayService.handler;

/**
 * 用于处理没有登录或token过期时的自定义返回结果
 */
//@Component
//public class RequestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    public class RequestAuthenticationEntryPoint{
//    @Override
//    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.OK);
//        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//        String body = JSONUtil.toJsonStr(new ResponseResultPo<>(1004, "Illegal token", null));
//        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
//        return response.writeWith(Mono.just(buffer));
//    }
}