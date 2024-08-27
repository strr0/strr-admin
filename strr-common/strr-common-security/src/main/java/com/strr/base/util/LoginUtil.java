package com.strr.base.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;
import java.util.Optional;

/**
 * 登录工具类
 */
public class LoginUtil {
    public static Optional<Map<String, Object>> getClaimsOpt() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(item -> item instanceof Jwt jwt ? jwt : null)
                .map(Jwt::getClaims);
    }

    public static Optional<Integer> getLoginIdOpt() {
        return getClaimsOpt().map(map -> (Long) map.get("id")).map(Long::intValue);
    }

    public static Integer getLoginId() {
        return getLoginIdOpt().orElse(null);
    }
}
