package com.strr.auth.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.strr.auth.handle.CustomAccessDeniedHandler;
import com.strr.auth.handle.CustomAccessTokenResponseHandler;
import com.strr.auth.handle.CustomErrorResponseHandler;
import com.strr.auth.jwt.JwkFactory;
import com.strr.auth.service.LoginUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2PasswordAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2PasswordAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

/**
 * 授權中心配置
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {
    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http, AuthenticationManager authenticationManager, OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) throws Exception {

        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // @formatter:off
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .tokenEndpoint(tokenEndpoint ->
                tokenEndpoint.accessTokenRequestConverters(converters -> {
                    converters.add(new OAuth2AuthorizationCodeAuthenticationConverter());
                    converters.add(new OAuth2RefreshTokenAuthenticationConverter());
                    converters.add(new OAuth2ClientCredentialsAuthenticationConverter());
                    converters.add(new OAuth2PasswordAuthenticationConverter());
                })
                .authenticationProvider(new OAuth2PasswordAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator))
                .accessTokenResponseHandler(new CustomAccessTokenResponseHandler())  // 登录成功处理器
                .errorResponseHandler(new CustomErrorResponseHandler())  // 登录失败处理器
            )
            .clientAuthentication(configurer -> {
                configurer.errorResponseHandler(new CustomErrorResponseHandler());  // 处理客户端认证异常
            })
            .oidc(Customizer.withDefaults());    // Enable OpenID Connect 1.0
        // @formatter:on

        // @formatter:off
        http.exceptionHandling(exceptions -> {
                exceptions.accessDeniedHandler(new CustomAccessDeniedHandler());  // 拒绝处理器
            });
        // @formatter:on
        return http.build();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(Environment environment) {
        String auth = environment.getProperty("module.auth", "");
        Map<String, Object> settings = new HashMap<>();
        settings.put(ConfigurationSettingNames.AuthorizationServer.AUTHORIZATION_ENDPOINT, auth + "/oauth2/authorize");
        settings.put(ConfigurationSettingNames.AuthorizationServer.DEVICE_AUTHORIZATION_ENDPOINT, auth + "/oauth2/device_authorization");
        settings.put(ConfigurationSettingNames.AuthorizationServer.DEVICE_VERIFICATION_ENDPOINT, auth + "/oauth2/device_verification");
        settings.put(ConfigurationSettingNames.AuthorizationServer.TOKEN_ENDPOINT, auth + "/oauth2/token");
        settings.put(ConfigurationSettingNames.AuthorizationServer.JWK_SET_ENDPOINT, auth + "/oauth2/jwks");
        settings.put(ConfigurationSettingNames.AuthorizationServer.TOKEN_REVOCATION_ENDPOINT, auth + "/oauth2/revoke");
        settings.put(ConfigurationSettingNames.AuthorizationServer.TOKEN_INTROSPECTION_ENDPOINT, auth + "/oauth2/introspect");
        settings.put(ConfigurationSettingNames.AuthorizationServer.OIDC_CLIENT_REGISTRATION_ENDPOINT, auth + "/connect/register");
        settings.put(ConfigurationSettingNames.AuthorizationServer.OIDC_USER_INFO_ENDPOINT, auth + "/userinfo");
        settings.put(ConfigurationSettingNames.AuthorizationServer.OIDC_LOGOUT_ENDPOINT, auth + "/connect/logout");
        return AuthorizationServerSettings.withSettings(settings).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        return JwkFactory.jwkSource();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator(JWKSource<SecurityContext> jwkSource, LoginUserService loginUserService) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        jwtGenerator.setJwtCustomizer(JwkFactory.jwtCustomizer(loginUserService));
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }
}
