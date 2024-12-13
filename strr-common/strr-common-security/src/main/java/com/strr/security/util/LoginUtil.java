package com.strr.security.util;

import com.strr.base.constant.Constant;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 登录工具类
 */
public class LoginUtil {
    public static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication);
    }

    public static Optional<Authentication> getAuthentication(Supplier<Authentication> authentication) {
        return Optional.ofNullable(authentication.get());
    }

    public static Optional<Map<String, Object>> getClaims() {
        return getAuthentication().map(Authentication::getPrincipal)
                .map(item -> item instanceof Jwt jwt ? jwt : null)
                .map(Jwt::getClaims);
    }

    public static Optional<Map<String, Object>> getClaims(Supplier<Authentication> authentication) {
        return getAuthentication(authentication).map(Authentication::getPrincipal)
                .map(item -> item instanceof Jwt jwt ? jwt : null)
                .map(Jwt::getClaims);
    }

    public static Long getLoginId() {
        return getClaims().map(map -> (Long) map.get(Constant.USER_ID)).orElse(null);
    }

    public static Long getLoginId(Supplier<Authentication> authentication) {
        return getClaims(authentication).map(map -> (Long) map.get(Constant.USER_ID)).orElse(null);
    }
}
