package com.ljl.opweGatewayService.filter;

import com.ljl.opweGatewayService.handler.JwtReactiveAuthenticationManager;
import com.ljl.opweGatewayService.utils.JwtUtil;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

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
    private final JwtUtil jwtUtil;
    private final ReactiveUserDetailsService userDetailsService;

//    @Autowired
//    public JwtAuthenticationFilter(JwtUtil jwtUtil){
//        this.jwtUtil = jwtUtil;
//    }
    public JwtAuthenticationFilter(JwtUtil jwtUtil, ReactiveUserDetailsService userDetailsService) {
        super(new JwtReactiveAuthenticationManager(jwtUtil, userDetailsService));  // Custom manager to handle JWT
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        setAuthenticationSuccessHandler(new WebFilterChainServerAuthenticationSuccessHandler());
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        // Validate token and proceed if valid
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            return jwtUtil.validateToken(token).flatMap(isValid -> {
                if (isValid) {
                    // Extract the username and load user details
                    String username = jwtUtil.extractUsername(token);
                    return userDetailsService.findByUsername(username).flatMap(userDetails -> {
                        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
                    });
                } else {
                    return chain.filter(exchange);  // Continue without authentication if token is invalid
                }
            });
        }

        // If no Authorization header or invalid format, proceed without authentication
        return chain.filter(exchange);
    }
}
