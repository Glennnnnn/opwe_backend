package com.ljl.opweGatewayService.config;

import com.ljl.opweGatewayService.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author Liu Jialin
 * @Date 2024/10/21 19:01
 * @PackageName com.ljl.opweGatewayService.config
 * @ClassName WebSecurityConfig
 * @Description TODO
 * @Version 1.0.0
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class ReactiveSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    public ReactiveSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Define the SecurityFilterChain bean, the main component of the new DSL
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http,
                                                      JwtAuthenticationFilter jwtAuthenticationFilter) {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(exchanges -> exchanges
                    .pathMatchers("/auth/login/**").permitAll()
                    .anyExchange().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION) // Add JWT filter
            .exceptionHandling(exceptionHandling ->
                    exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            );// Custom Authentication Entry Point

        return http.build();
    }

    // Custom Authentication Entry Point
    public static class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
        @Override
        public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
            return exchange.getResponse().writeWith(Mono.just(bufferFactory.wrap("Unauthorized".getBytes())));
        }
    }
}
