package com.ljl.opweAuthService.controller;

import com.ljl.opweAuthService.entity.common.Result;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.util.Base64;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.*;

import static com.ljl.opweAuthService.entity.constants.SecurityConstants.*;
import static com.ljl.opweAuthService.utils.Base64Utils.base64Encode;

/**
 * @Author Liu Jialin
 * @Date 2024/5/21 19:29
 * @PackageName com.ljl.opweAuthService.controller
 * @ClassName OauthConsentController
 * @Description TODO
 * @Version 1.0.0
 */
@RestController
public class OauthConsentController {

    @Autowired
    RegisteredClientRepository registeredClientRepository;

    @Autowired
    OAuth2AuthorizationConsentService authorizationConsentService;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @ResponseBody
    @GetMapping(value = "/oauth2/consent/redirect")
    public void consentRedirect(HttpSession session,
                                          HttpServletRequest request,
                                          HttpServletResponse response,
                                          @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                                          @RequestParam(OAuth2ParameterNames.STATE) String state,
                                          @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                                          @RequestParam(name = OAuth2ParameterNames.USER_CODE, required = false) String userCode) throws IOException {

        // 携带当前请求参数与nonceId重定向至前端页面
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(CONSENT_PAGE_URI)
                .queryParam(OAuth2ParameterNames.SCOPE, UriUtils.encode(scope, StandardCharsets.UTF_8))
                .queryParam(OAuth2ParameterNames.STATE, UriUtils.encode(state, StandardCharsets.UTF_8))
                .queryParam(OAuth2ParameterNames.CLIENT_ID, clientId)
                .queryParam(OAuth2ParameterNames.USER_CODE, userCode);

        String uriString = uriBuilder.build(Boolean.TRUE).toUriString();
//        if (ObjectUtils.isEmpty(userCode) || !UrlUtils.isAbsoluteUrl(DEVICE_ACTIVATE_URI)) {
//            // 不是设备码模式或者设备码验证页面不是前后端分离的，无需返回json，直接重定向
//            redirectStrategy.sendRedirect(request, response, uriString);
//            return null;
//        }
        // 兼容设备码，需响应JSON，由前端进行跳转

        response.addHeader("nonceId", session.getId());


        this.redirectStrategy.sendRedirect(request, response, uriString);
    }


    @ResponseBody
    @GetMapping(value = "/oauth2/consent/parameters")
    public Result<Map<String, Object>> consentParameters(Principal principal,
                                                         @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                                                         @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                                                         @RequestParam(OAuth2ParameterNames.STATE) String state,
                                                         @RequestParam(name = OAuth2ParameterNames.USER_CODE, required = false) String userCode) {

        // 获取consent页面所需的参数
        Map<String, Object> consentParameters = getConsentParameters(scope, state, clientId, userCode, principal);

        return Result.success(consentParameters);
    }

    private static Set<ScopeWithDescription> withDescription(Set<String> scopes) {
        Set<ScopeWithDescription> scopeWithDescriptions = new HashSet<>();
        for (String scope : scopes) {
            scopeWithDescriptions.add(new ScopeWithDescription(scope));

        }
        return scopeWithDescriptions;
    }

    /**
     * 根据授权确认相关参数获取授权确认与未确认的scope相关参数
     *
     * @param scope     scope权限
     * @param state     state
     * @param clientId  客户端id
     * @param userCode  设备码授权流程中的用户码
     * @param principal 当前认证信息
     * @return 页面所需数据
     */
    private Map<String, Object> getConsentParameters(String scope,
                                                     String state,
                                                     String clientId,
                                                     String userCode,
                                                     Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Remove scopes that were already approved
        Set<String> scopesToApprove = new HashSet<>();
        Set<String> previouslyApprovedScopes = new HashSet<>();
        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
        if (registeredClient == null) {
            throw new RuntimeException("客户端不存在");
        }
        OAuth2AuthorizationConsent currentAuthorizationConsent =
                this.authorizationConsentService.findById(registeredClient.getId(), principal.getName());
        Set<String> authorizedScopes;
        if (currentAuthorizationConsent != null) {
            authorizedScopes = currentAuthorizationConsent.getScopes();
        } else {
            authorizedScopes = Collections.emptySet();
        }
        for (String requestedScope : StringUtils.delimitedListToStringArray(scope, " ")) {
            if (OidcScopes.OPENID.equals(requestedScope)) {
                continue;
            }
            if (authorizedScopes.contains(requestedScope)) {
                previouslyApprovedScopes.add(requestedScope);
            } else {
                scopesToApprove.add(requestedScope);
            }
        }

        Map<String, Object> parameters = new HashMap<>(7);
        parameters.put("clientId", registeredClient.getClientId());
        parameters.put("clientName", registeredClient.getClientName());
        parameters.put("state", state);
        parameters.put("scopes", withDescription(scopesToApprove));
        parameters.put("previouslyApprovedScopes", withDescription(previouslyApprovedScopes));
        parameters.put("principalName", principal.getName());
        parameters.put("userCode", userCode);
        if (StringUtils.hasText(userCode)) {
            parameters.put("requestURI", "/oauth2/device_verification");
        } else {
            parameters.put("requestURI", "/oauth2/authorize");
        }
        return parameters;
    }

    @Data
    public static class ScopeWithDescription {
        private static final String DEFAULT_DESCRIPTION = "UNKNOWN SCOPE - We cannot provide information about this permission, use caution when granting this.";
        private static final Map<String, String> scopeDescriptions = new HashMap<>();

        static {
            scopeDescriptions.put(
                    OidcScopes.PROFILE,
                    "This application will be able to read your profile information."
            );
            scopeDescriptions.put(
                    "message.read",
                    "This application will be able to read your message."
            );
            scopeDescriptions.put(
                    "message.write",
                    "This application will be able to add new messages. It will also be able to edit and delete existing messages."
            );
            scopeDescriptions.put(
                    "other.scope",
                    "This is another scope example of a scope description."
            );
        }

        public final String scope;
        public final String description;

        ScopeWithDescription(String scope) {
            this.scope = scope;
            this.description = scopeDescriptions.getOrDefault(scope, DEFAULT_DESCRIPTION);
        }
    }

}
