package com.ljl.opweAuthService.repositpry;

import com.ljl.opweAuthService.model.security.SupplierDeferredSecurityContext;
import com.ljl.opweAuthService.utils.Base64Utils;
import com.ljl.opweAuthService.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.context.SecurityContextRepository;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpRequestResponseHolder;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.function.Supplier;

import static com.ljl.opweAuthService.entity.constants.RedisConstants.DEFAULT_TIMEOUT_SECONDS;
import static com.ljl.opweAuthService.entity.constants.RedisConstants.SECURITY_CONTEXT_PREFIX_KEY;
import static com.ljl.opweAuthService.entity.constants.SecurityConstants.NONCE_HEADER_NAME;


/**
 * @Author Liu Jialin
 * @Date 2024/4/23 14:53
 * @PackageName com.ljl.opweAuthService.handler
 * @ClassName RedisSecurityContextRepositoryHandler
 * @Description TODO
 * @Version 1.0.0
 */
@Component
@Deprecated
//public class RedisSecurityContextRepositoryHandler implements SecurityContextRepository {
public class RedisSecurityContextRepositoryHandler{
//    @Autowired
//    private RedisUtils redisUtils;
//
//    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
//            .getContextHolderStrategy();
//
//
//    @Override
//    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
////        HttpServletRequest request = requestResponseHolder.getRequest();
////        return readSecurityContextFromRedis(request);
//        // 方法已过时，使用 loadDeferredContext 方法
//        throw new UnsupportedOperationException("Method deprecated.");
//    }
//
//    @Override
//    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
//        String nonce = getNonce(request);
//        if (ObjectUtils.isEmpty(nonce)) {
//            return;
//        }
//
//        // 如果当前的context是空的，则移除
//        SecurityContext emptyContext = this.securityContextHolderStrategy.createEmptyContext();
//        if (emptyContext.equals(context)) {
//            redisUtils.delete((SECURITY_CONTEXT_PREFIX_KEY + request.getSession().getId()));
//        } else {
//            // 保存认证信息
//            redisUtils.set((SECURITY_CONTEXT_PREFIX_KEY + request.getSession().getId()), context, DEFAULT_TIMEOUT_SECONDS);
//        }
//    }
//
//    @Override
//    public boolean containsContext(HttpServletRequest request) {
//        String nonce = getNonce(request);
//        if (ObjectUtils.isEmpty(nonce)) {
//            return false;
//        }
//        // 检验当前请求是否有认证信息
//        return redisUtils.get((SECURITY_CONTEXT_PREFIX_KEY + nonce)) != null;
//    }
//
//    @Override
//    public DeferredSecurityContext loadDeferredContext(HttpServletRequest request) {
//        Supplier<SecurityContext> supplier = () -> readSecurityContextFromRedis(request);
//        return new SupplierDeferredSecurityContext(supplier, this.securityContextHolderStrategy);
//    }
//
//    /**
//     * 从redis中获取认证信息
//     *
//     * @param request 当前请求
//     * @return 认证信息
//     */
//    private SecurityContext readSecurityContextFromRedis(HttpServletRequest request) {
//        if (request == null) {
//            return null;
//        }
//
//        String nonce = getNonce(request);
//        if (ObjectUtils.isEmpty(nonce)) {
//            return redisUtils.get((SECURITY_CONTEXT_PREFIX_KEY + Base64Utils.base64Decode(request.getSession().getId())));
//        }
//
//        // 根据缓存id获取认证信息
//        return redisUtils.get((SECURITY_CONTEXT_PREFIX_KEY + nonce));
//    }
//
//    /**
//     * 先从请求头中找，找不到去请求参数中找，找不到获取当前session的id
//     *  2023-07-11新增逻辑：获取当前session的sessionId
//     *
//     * @param request 当前请求
//     * @return 随机字符串(sessionId)，这个字符串本来是前端生成，现在改为后端获取的sessionId
//     */
//    private String getNonce(HttpServletRequest request) {
//        String nonce = request.getHeader(NONCE_HEADER_NAME);
//        if (ObjectUtils.isEmpty(nonce)) {
//            nonce = request.getParameter(NONCE_HEADER_NAME);
//            HttpSession session = request.getSession(Boolean.FALSE);
//            if (ObjectUtils.isEmpty(nonce) && session != null) {
//                nonce = session.getId();
//            }
//        }
//        return nonce;
//    }
}
