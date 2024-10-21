package com.ljl.opweGatewayService.handler;

import com.ljl.opweGatewayService.utils.JwtUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

/**
 * @Author Liu Jialin
 * @Date 2024/10/21 19:25
 * @PackageName com.ljl.opweGatewayService.handler
 * @ClassName JwtReactiveAuthenticationManager
 * @Description TODO
 * @Version 1.0.0
 */
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {
    private JwtUtil jwtUtil;
    private ReactiveUserDetailsService userDetailsService;

    public JwtReactiveAuthenticationManager(JwtUtil jwtUtil, ReactiveUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String username = jwtUtil.extractUsername(token);

        return userDetailsService.findByUsername(username)
                .flatMap(userDetails -> {
                    if (jwtUtil.validateToken(token, userDetails)) {
                        return Mono.just(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
                    }
                    return Mono.empty();
                });
    }
}
