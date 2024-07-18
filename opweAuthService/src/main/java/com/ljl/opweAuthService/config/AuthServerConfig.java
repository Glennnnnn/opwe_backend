package com.ljl.opweAuthService.config;

import com.ljl.opweAuthService.entity.constants.SecurityConstants;
import com.ljl.opweAuthService.handler.*;
import com.ljl.opweAuthService.repositpry.RedisSecurityContextRepositoryHandler;
import com.ljl.opweAuthService.service.impl.AuthUserDetailService;
import com.ljl.opweAuthService.utils.SecurityUtils;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.stream.Collectors;

import static com.ljl.opweAuthService.entity.constants.SecurityConstants.CONSENT_PAGE_URI;

/**
 * @Author Liu Jialin
 * @Date 2024/4/9 20:25
 * @PackageName com.ljl.opweAuthService.config
 * @ClassName SecutiryConfig
 * @Description TODO
 * @Version 1.0.0
 */
@Configuration
@EnableWebSecurity
public class AuthServerConfig {

    @Value("${server.port}")
    int serverPort;
    private static final String CUSTOM_CONSENT_REDIRECT_URI = "/oauth2/consent/redirect";

    @Autowired
    private RedisSecurityContextRepositoryHandler redisSecurityContextRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置用户信息，或者配置用户数据来源，主要用于用户的检索。
     *
     * @return
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // 使用redis存储、读取登录的认证信息
        http.securityContext(context -> context.securityContextRepository(redisSecurityContextRepository));

        http
            //为 OAuth2 认证服务器添加 OIDC 支持
            .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .oidc(Customizer.withDefaults())
            .authorizationEndpoint(authorizationEndpoint -> {
                // 校验授权确认页面是否为完整路径；是否是前后端分离的页面
                boolean absoluteUrl = UrlUtils.isAbsoluteUrl(CUSTOM_CONSENT_REDIRECT_URI);
                // 如果是分离页面则重定向，否则转发请求
                authorizationEndpoint.consentPage(CUSTOM_CONSENT_REDIRECT_URI);

                    // 适配前后端分离的授权确认页面，成功/失败响应json
                authorizationEndpoint.errorResponseHandler(new ConsentAuthenticationFailureHandler());
                authorizationEndpoint.authorizationResponseHandler(new ConsentAuthorizationSuccessHandler());

            }
        );


        http
            .exceptionHandling(e -> e
                    .defaultAuthenticationEntryPointFor(
                            new CustomAuthenticationEntryPoint(),
                            new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                    )
            )
            //当未经身份验证的用户尝试访问受保护的资源时，将用户重定向到Security的默认登录页面
            .oauth2ResourceServer((resourceServer) -> resourceServer
                    .jwt(Customizer.withDefaults())
            );

        //配置 OAuth2 资源服务器以使用 JWT 令牌进行身份验证
        return http.build();
    }

    @Autowired
    AuthUserDetailService authUserDetailService;

    /**
     * 这个也是个Spring Security的过滤器链，用于Spring Security的身份认证。
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.addFilter(corsFilter());
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((authorize) ->  authorize
                .requestMatchers("/assets/**", "/webjars/**", "/login", "/oauth2/consent/parameters").permitAll()
                .anyRequest().authenticated()
            )
            // Form login handles the redirect to the login page from the
            // authorization server filter chain
            .formLogin(formLogin ->
                    formLogin.loginPage("/login")
                            // 登录成功和失败改为写回json，不重定向了
                            .successHandler(new LoginSuccessHandler())
                            .failureHandler(new LoginFailureHandler())
            );

        // 添加BearerTokenAuthenticationFilter，将认证服务当做一个资源服务，解析请求头中的token
        http.oauth2ResourceServer((resourceServer) -> resourceServer
                .jwt(Customizer.withDefaults())
                .accessDeniedHandler(SecurityUtils::exceptionHandler)
                .authenticationEntryPoint(SecurityUtils::exceptionHandler)
        );

        http
            // 当未登录时访问认证端点时重定向至login页面
            .exceptionHandling((exceptions) -> exceptions
                    .defaultAuthenticationEntryPointFor(
                            new CustomAuthenticationEntryPoint(),
                            new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                    )
            );
//        // 联合身份认证
//        http.oauth2Login(oauth2Login -> oauth2Login
//                .loginPage("http://127.0.0.1:3000/login")
//                .authorizationEndpoint(authorization -> authorization
//                        .authorizationRequestResolver(this.authorizationRequestResolver(clientRegistrationRepository))
//                )
//                .tokenEndpoint(token -> token
//                        .accessTokenResponseClient(this.accessTokenResponseClient())
//                )
//        );
        // 使用redis存储、读取登录的认证信息
        http.securityContext(context -> context.securityContextRepository(redisSecurityContextRepository));

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new AuthUserDetailService();
//    }

    /**
     * 跨域过滤器配置
     *
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {

        // 初始化cors配置对象
        CorsConfiguration configuration = new CorsConfiguration();
        // 设置允许跨域的域名,如果允许携带cookie的话,路径就不能写*号, *表示所有的域名都可以跨域访问
        //configuration.addAllowedOrigin("*");
        // 设置允许跨域的域名,如果允许携带cookie的话,路径就不能写*号, *表示所有的域名都可以跨域访问
//        configuration.addAllowedOrigin("*");
        configuration.addAllowedOriginPattern("*");
        // 设置跨域访问可以携带cookie
        configuration.setAllowCredentials(true);
        // 允许所有的请求方法 ==> GET POST PUT Delete
        configuration.addAllowedMethod("*");
        // 允许携带任何头信息
        configuration.addAllowedHeader("*");
        // 初始化cors配置源对象
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();

        // 给配置源对象设置过滤的参数
        // 参数一: 过滤的路径 == > 所有的路径都要求校验是否跨域
        // 参数二: 配置类
        configurationSource.registerCorsConfiguration("/**", configuration);

        // 返回配置好的过滤器
        return new CorsFilter(configurationSource);
    }

    /**
     * 自定义jwt，将权限信息放至jwt中
     *
     * @return OAuth2TokenCustomizer的实例
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer() {
        return context -> {
            // 检查登录用户信息是不是UserDetails，排除掉没有用户参与的流程
            if (context.getPrincipal().getPrincipal() instanceof UserDetails user) {
                // 获取申请的scopes
                Set<String> scopes = context.getAuthorizedScopes();
                // 获取用户的权限
                Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
                // 提取权限并转为字符串
                Set<String> authoritySet = Optional.ofNullable(authorities).orElse(Collections.emptyList()).stream()
                        // 获取权限字符串
                        .map(GrantedAuthority::getAuthority)
                        // 去重
                        .collect(Collectors.toSet());

                // 合并scope与用户信息
                authoritySet.addAll(scopes);

                JwtClaimsSet.Builder claims = context.getClaims();
                // 将权限信息放入jwt的claims中（也可以生成一个以指定字符分割的字符串放入）
                claims.claim(SecurityConstants.AUTHORITIES_KEY, authoritySet);
                // 放入其它自定内容
                // 角色、头像...
            }
        };
    }

    /**
     * 自定义jwt解析器，设置解析出来的权限信息的前缀与在jwt中的key
     *
     * @return jwt解析器 JwtAuthenticationConverter
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 设置解析权限信息的前缀，设置为空是去掉前缀
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        // 设置权限信息在jwt claims中的key
        grantedAuthoritiesConverter.setAuthoritiesClaimName(SecurityConstants.AUTHORITIES_KEY);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    /*
    向OAuth2认证服务器注册一个客户端应用程序进行授权
    该 Bean 提供了一个内存中的注册客户端存储，用于 OAuth2 认证服务器的客户端授权
    客户端应使用此处设置的值作为配置项
    */
    @Bean
//    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        //生成随机UUID作为客户端唯一标识，避免多个客户端时ID冲突
//        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("opwe-client") //设置授权客户端ID
//                .clientSecret(passwordEncoder().encode("123456")) //设置客户端密钥
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                /*
//                    设置客户端的重定向 URI，当用户授权后，OAuth2 认证服务器将重定向到该 URI
//                    由于OAuth2认证服务器的安全性设置，此处必须使用127.0.0.1
//                    使用localhost会导致拒绝重定向
//                 */
//                //.redirectUri("http://spring-oauth-client:9001/login/oauth2/code/messaging-client-oidc")
////                .redirectUri("http://127.0.0.1:8085/authorized")
//                .redirectUri("https://www.baidu.com")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .clientSettings(ClientSettings.builder()
//                        //client端请求 jwt解密算法
////                        .jwkSetUrl("http://localhost:" + serverPort + "/oauth2/jwks")
//                        .requireAuthorizationConsent(true)
//                        .build())
////                .scope("message.read")
////                .scope("message.write")
////                .tokenSettings(TokenSettings.builder()
////                        //使用透明方式，默认是 OAuth2TokenFormat SELF_CONTAINED,生成 JWT 加密方式
////                        // SELF_CONTAINED 生成 JWT 加密方式
////                        //.idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)
////                        //.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
////                        .authorizationCodeTimeToLive(Duration.ofHours(1))
////                        .accessTokenTimeToLive(Duration.ofHours(2))
////                        .refreshTokenTimeToLive(Duration.ofHours(3))
////                        .reuseRefreshTokens(false)
////                        .build())
//                .build();
//
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                // 客户端id
                .clientId("opwe-client")
                // 客户端秘钥，使用密码解析器加密
                .clientSecret(passwordEncoder().encode("123456"))
                // 客户端认证方式，基于请求头的认证
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // 配置资源服务器使用该客户端获取授权时支持的方式
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                // 客户端添加自定义认证
                //.authorizationGrantType(new AuthorizationGrantType(SecurityConstants.GRANT_TYPE_SMS_CODE))
                // 授权码模式回调地址，oauth2.1已改为精准匹配，不能只设置域名，并且屏蔽了localhost，本机使用127.0.0.1访问
                .redirectUri("http://127.0.0.1:8085/login/oauth2/code/messaging-client-oidc")
                .redirectUri("http://127.0.0.1:3000")
                // 该客户端的授权范围，OPENID与PROFILE是IdToken的scope，获取授权时请求OPENID的scope时认证服务会返回IdToken
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                // 自定scope
                .scope("message.read")
                .scope("message.write")
                // 客户端设置，设置用户需要确认授权
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        if (null == registeredClientRepository.findByClientId(registeredClient.getClientId())) {
            registeredClientRepository.save(registeredClient);
        }

        return registeredClientRepository;
//        return new InMemoryRegisteredClientRepository(registeredClient);

    }

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    //
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    //提供Jwt令牌

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * 配置Authorization Server实例
     *
     * @return
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }


}
