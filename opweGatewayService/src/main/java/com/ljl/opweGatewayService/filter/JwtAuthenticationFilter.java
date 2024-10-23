package com.ljl.opweGatewayService.filter;

import com.ljl.opweGatewayService.handler.JwtReactiveAuthenticationManager;
import com.ljl.opweGatewayService.service.feignClients.AuthServiceClient;
import com.ljl.opweGatewayService.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

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
        this.authServiceClient = authServiceClient;
//        setAuthenticationSuccessHandler(new WebFilterChainServerAuthenticationSuccessHandler());
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        // Validate token and proceed if valid
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            return jwtUtil.validateToken(token).flatMap(isValid -> {
                if (isValid) {
                    // Extract username from the token
                    String username = jwtUtil.extractUsername(token);

                    // Load user details
                    return authServiceClient.loadUserByUsername(username)
                            .flatMap(response -> {
                                // Check if the response is valid
                                if (response.getCode() == 200 && response.getData() != null) {
                                    UserDetails userDetails = response.getData();
                                    // Check for required authority (replace "REQUIRED_AUTHORITY" with actual authority)
                                    Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                    return chain.filter(exchange)
                                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
                                } else {
                                    return chain.filter(exchange); // Proceed without authentication if user not found
                                }
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
