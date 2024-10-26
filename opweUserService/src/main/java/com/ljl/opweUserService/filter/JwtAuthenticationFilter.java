package com.ljl.opweUserService.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Liu Jialin
 * @Date 2023/9/2 17:00
 * @PackageName com.ljl.inventory.filter
 * @ClassName JwtAuthenticationFilter
 * @Description TODO
 * @Version 1.0.0
 */
// Comment out for no jwt check
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    public JwtAuthenticationFilter() {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //fetch token
        String userId = null;
        String authorizationHeader = request.getHeader("X-Custom-Auth");
        if (authorizationHeader != null) {
            // Parse the custom auth JSON from the header
            JSONObject authJson = JSONObject.parseObject(authorizationHeader);

            // Extract fields from the parsed JSON
            String username = authJson.getString("username");
            JSONArray authoritiesArray = authJson.getJSONArray("authorities");

            // Convert authorities to List<GrantedAuthority>
            List<GrantedAuthority> authorities = authoritiesArray.stream()
                    .map(auth -> new SimpleGrantedAuthority(((JSONObject) authoritiesArray.get(0)).get("authority").toString())
            ).collect(Collectors.toList());

            // Create the authentication token
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            // Set the authentication in the SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}

