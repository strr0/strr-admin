package com.strr.auth.jwt;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.strr.auth.model.LoginUserDetails;
import com.strr.auth.service.LoginUserService;
import com.strr.constant.Constant;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

public class JwkFactory {
    public static JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    /**
     * 自定义 JWT 內容
     */
    public static OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(LoginUserService loginUserService) {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) && context.getPrincipal() instanceof UsernamePasswordAuthenticationToken principal) {
                if (principal.getPrincipal() instanceof LoginUserDetails user) {
                    JwtClaimsSet.Builder claims = context.getClaims();
                    claims.claim(Constant.USER_ID, user.getId());
                    claims.claim(Constant.USERNAME, user.getUsername());
                    // 获取用户权限
                    claims.claim(Constant.PERMS, loginUserService.listPermsByUserId(user.getId()));
                }
            }
        };
    }
}
