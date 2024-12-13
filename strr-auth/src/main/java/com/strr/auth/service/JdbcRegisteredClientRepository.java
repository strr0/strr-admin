package com.strr.auth.service;

import com.strr.base.util.StringUtil;
import com.strr.system.api.RemoteOauthClientService;
import com.strr.system.api.model.OauthClient;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

/**
 * 客户端配置
 */
@Component
public class JdbcRegisteredClientRepository implements RegisteredClientRepository {
    private static final Long ACCESS_TIMEOUT = 3600L;
    private static final Long REFRESH_TIMEOUT = 604800L;

    @DubboReference
    private RemoteOauthClientService remoteOauthClientService;

    @Override
    public void save(RegisteredClient registeredClient) {
    }

    @Override
    public RegisteredClient findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        OauthClient clientInfo = remoteOauthClientService.getClientInfo(clientId);
        if (clientInfo == null) {
            return null;
        }
        // @formatter:off
        RegisteredClient.Builder builder = RegisteredClient.withId(String.valueOf(clientInfo.getId()))
            .clientId(clientInfo.getClientId())
            .clientSecret(clientInfo.getClientSecret())
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        // @formatter:on

        // 授权方式
        for (String grantType : clientInfo.getGrantTypes()) {
            if (StringUtil.isNotBlank(grantType)) {
                builder.authorizationGrantType(new AuthorizationGrantType(grantType));
            }
        }

        // 作用域
        for (String scope : clientInfo.getScopes()) {
            if (StringUtil.isNotBlank(scope)) {
                builder.scope(scope);
            }
        }

        // 回调地址
        for (String redirectUri : clientInfo.getRedirectUris()) {
            if (StringUtil.isNotBlank(redirectUri)) {
                builder.redirectUri(redirectUri);
            }
        }

        // @formatter:off
        return builder.tokenSettings(TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(clientInfo.getAccessTimeout()).orElse(ACCESS_TIMEOUT)))
                .refreshTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(clientInfo.getRefreshTimeout()).orElse(REFRESH_TIMEOUT)))
                .build())
            .build();
        // @formatter:on
    }
}
