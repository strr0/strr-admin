package com.strr.auth.service;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * token 存储
 *
 * @author lengleng
 */
@Service
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {
    private static final String AUTHORIZATION = "oauth2";
    private static final Long TIMEOUT = 10L;
    @Resource
    private RedisTemplate<String, OAuth2Authorization> redisTemplate;

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");

        getState(authorization).ifPresent(token -> {
            redisTemplate.setValueSerializer(RedisSerializer.java());
            redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.STATE, token), authorization, TIMEOUT, TimeUnit.MINUTES);
        });

        getCode(authorization).ifPresent(token -> {
            OAuth2AuthorizationCode code = token.getToken();
            long between = ChronoUnit.MINUTES.between(code.getIssuedAt(), code.getExpiresAt());
            redisTemplate.setValueSerializer(RedisSerializer.java());
            redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.CODE, code.getTokenValue()), authorization, between, TimeUnit.MINUTES);
        });

        getRefreshToken(authorization).ifPresent(token -> {
            OAuth2RefreshToken refreshToken = token.getToken();
            long between = ChronoUnit.SECONDS.between(refreshToken.getIssuedAt(), refreshToken.getExpiresAt());
            redisTemplate.setValueSerializer(RedisSerializer.java());
            redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()), authorization, between, TimeUnit.SECONDS);
        });

        getAccessToken(authorization).ifPresent(token -> {
            OAuth2AccessToken accessToken = token.getToken();
            long between = ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt());
            redisTemplate.setValueSerializer(RedisSerializer.java());
            redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()), authorization, between, TimeUnit.SECONDS);
        });
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        List<String> keys = new ArrayList<>();

        getState(authorization).ifPresent(token -> {
            keys.add(buildKey(OAuth2ParameterNames.STATE, token));
        });

        getCode(authorization).ifPresent(token -> {
            OAuth2AuthorizationCode code = token.getToken();
            keys.add(buildKey(OAuth2ParameterNames.CODE, code.getTokenValue()));
        });

        getRefreshToken(authorization).ifPresent(token -> {
            OAuth2RefreshToken refreshToken = token.getToken();
            keys.add(buildKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()));
        });

        getAccessToken(authorization).ifPresent(token -> {
            OAuth2AccessToken accessToken = token.getToken();
            keys.add(buildKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()));
        });

        redisTemplate.delete(keys);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        Assert.notNull(tokenType, "tokenType cannot be null");
        redisTemplate.setValueSerializer(RedisSerializer.java());
        return redisTemplate.opsForValue().get(buildKey(tokenType.getValue(), token));
    }

    private static String buildKey(String type, String token) {
        return String.format("%s::%s::%s", AUTHORIZATION, type, token);
    }

    private static Optional<String> getState(OAuth2Authorization authorization) {
        return Optional.ofNullable(authorization).map(auth -> auth.getAttribute("state"));
    }

    private static Optional<OAuth2Authorization.Token<OAuth2AuthorizationCode>> getCode(OAuth2Authorization authorization) {
        return Optional.ofNullable(authorization).map(auth -> auth.getToken(OAuth2AuthorizationCode.class));
    }

    private static Optional<OAuth2Authorization.Token<OAuth2RefreshToken>> getRefreshToken(OAuth2Authorization authorization) {
        return Optional.ofNullable(authorization).map(OAuth2Authorization::getRefreshToken);
    }

    private static Optional<OAuth2Authorization.Token<OAuth2AccessToken>> getAccessToken(OAuth2Authorization authorization) {
        return Optional.ofNullable(authorization).map(OAuth2Authorization::getAccessToken);
    }
}
