package com.ljl.opweGatewayService.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ljl.opweGatewayService.entity.common.ResponseResultPo;
import com.ljl.opweGatewayService.handler.JwtReactiveAuthenticationManager;
import com.ljl.opweGatewayService.service.feignClients.AuthServiceClient;
import com.ljl.opweGatewayService.service.feignClients.AuthServiceClientOld;
import com.ljl.opweGatewayService.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/10/21 18:25
 * @PackageName com.ljl.opweGatewayService.filter
 * @ClassName JwtAuthenticationFilter
 * @Description TODO
 * @Version 1.0.0
 */
@Component
public class JwtAuthenticationFilter extends AuthenticationWebFilter {
    private JwtUtil jwtUtil;
    private ReactiveUserDetailsService userDetailsService;
    private AuthServiceClient authServiceClient;

//    @Autowired
//    public JwtAuthenticationFilter(JwtUtil jwtUtil){
//        this.jwtUtil = jwtUtil;
//    }


    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil, ReactiveUserDetailsService userDetailsService, AuthServiceClient authServiceClient) {
        super(new JwtReactiveAuthenticationManager(jwtUtil, userDetailsService));  // Custom manager to handle JWT
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authServiceClient = authServiceClient; // Replace with your Eureka service name or endpoint
    }
//        setAuthenticationSuccessHandler(new WebFilterChainServerAuthenticationSuccessHandler());


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        // Validate token and proceed if valid
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
//            chain.filter(exchange)
//                    .contextWrite(Context.of("authToken", token));
            return jwtUtil.validateToken(token).flatMap(isValid -> {
                if (isValid) {
                    // Extract username from the token
                    //String username = jwtUtil.extractUsername(token);

                    // Load user details
                    return Mono.fromCallable(() -> authServiceClient.verifyUser(authHeader))
                            .subscribeOn(Schedulers.boundedElastic()) // Offload to avoid blocking WebFlux event loop
                            .flatMap(responseMono -> responseMono // `responseMono` is the Mono<ResponseResultPo<Authentication>>
                                    .flatMap(response -> {
                                        System.out.println("Processing response...");

                                        // Now you can access getCode() and getData() methods on the response
                                        if (response.getCode() == 200 && response.getData() != null) {
//                                            UsernamePasswordAuthenticationToken auth = response.getData();
                                            System.out.println("Authenticated user: " + response.getData());
                                            JSONObject jsonObject = response.getData();

                                            // Extract the necessary fields from JSONObject
                                            String username = jsonObject.getString("username"); // Adjust the key as needed
                                            String password = jsonObject.getString("password"); // Adjust the key as needed
                                            List<GrantedAuthority> authorities = jsonObject.getJSONArray("authorities")
                                                    .toJavaList(GrantedAuthority.class); // Adjust the key as needed

                                            // Create the UsernamePasswordAuthenticationToken
                                            UsernamePasswordAuthenticationToken auth =
                                                    new UsernamePasswordAuthenticationToken(username, password, authorities);
                                            // Set Authentication in ReactiveSecurityContext and filter the chain
                                            return Mono.defer(() -> chain.filter(exchange)
                                                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)));
//                                            return chain.filter(exchange);
                                        } else {
                                            // If the user is not found, proceed without authentication
                                            System.out.println("User not found, proceeding without authentication.");
                                            return chain.filter(exchange);
                                        }
                                    })
                            )
                            .onErrorResume(e -> {
                                // Handle any errors from the Feign client
                                System.err.println("Error occurred while verifying user: " + e.getMessage());
                                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                                return Mono.empty(); // Return empty Mono on error
                            });
                } else {
                    return onFailure(exchange, "Invalid token."); // Token is invalid, proceed without authentication
                }
            });
        }
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return Mono.empty();
    }

    // Method to handle failures
    private Mono<Void> onFailure(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }

}
