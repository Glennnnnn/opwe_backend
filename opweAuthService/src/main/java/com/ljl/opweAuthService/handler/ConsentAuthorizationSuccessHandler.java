package com.ljl.opweAuthService.handler;

import com.ljl.opweAuthService.entity.common.Result;
import com.ljl.opweAuthService.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.ljl.opweAuthService.entity.constants.SecurityConstants.CONSENT_PAGE_URI;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.INVALID_REQUEST;

/**
 * @Author Liu Jialin
 * @Date 2024/5/20 20:55
 * @PackageName com.ljl.opweAuthService.handler
 * @ClassName ConsentAuthorizationResponseHandler
 * @Description TODO
 * @Version 1.0.0
 */
public class ConsentAuthorizationSuccessHandler implements AuthenticationSuccessHandler {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获取将要重定向的回调地址
        String redirectUri = this.getAuthorizationResponseUri(authentication);
//        if (request.getMethod().equals(HttpMethod.POST.name()) && UrlUtils.isAbsoluteUrl(CONSENT_PAGE_URI)) {
            // 如果是post请求并且CONSENT_PAGE_URI是完整的地址，则响应json
        Result<String> success = Result.success(redirectUri);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JsonUtils.objectCovertToJson(success));
        response.getWriter().flush();
        return;
//        }
        // 否则重定向至回调地址
//        this.redirectStrategy.sendRedirect(request, response, redirectUri);
    }

    /**
     * 获取重定向的回调地址
     *
     * @param authentication 认证信息
     * @return 地址
     */
    private String getAuthorizationResponseUri(Authentication authentication) {

        OAuth2AuthorizationCodeRequestAuthenticationToken authorizationCodeRequestAuthentication =
                (OAuth2AuthorizationCodeRequestAuthenticationToken) authentication;
        if (ObjectUtils.isEmpty(authorizationCodeRequestAuthentication.getRedirectUri())) {
            String authorizeUriError = "Redirect uri is not null";
            throw new OAuth2AuthorizationCodeRequestAuthenticationException(new OAuth2Error(INVALID_REQUEST, authorizeUriError, (null)), authorizationCodeRequestAuthentication);
        }

        if (authorizationCodeRequestAuthentication.getAuthorizationCode() == null) {
            String authorizeError = "AuthorizationCode is not null";
            throw new OAuth2AuthorizationCodeRequestAuthenticationException(new OAuth2Error(INVALID_REQUEST, authorizeError, (null)), authorizationCodeRequestAuthentication);
        }

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(authorizationCodeRequestAuthentication.getRedirectUri())
                .queryParam(OAuth2ParameterNames.CODE, authorizationCodeRequestAuthentication.getAuthorizationCode().getTokenValue());
        if (StringUtils.hasText(authorizationCodeRequestAuthentication.getState())) {
            uriBuilder.queryParam(
                    OAuth2ParameterNames.STATE,
                    UriUtils.encode(authorizationCodeRequestAuthentication.getState(), StandardCharsets.UTF_8));
        }
        // build(true) -> Components are explicitly encoded
        return uriBuilder.build(true).toUriString();

    }
}
