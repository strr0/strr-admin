package com.strr.auth.jwt;

import com.strr.auth.model.LoginUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * 自定义 JWT 內容
 */
public class CustomJwtCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
    @Override
    public void customize(JwtEncodingContext context) {
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) && context.getPrincipal() instanceof UsernamePasswordAuthenticationToken principal) {
            if (principal.getPrincipal() instanceof LoginUserDetails user) {
                JwtClaimsSet.Builder claims = context.getClaims();
                claims.claim("id", user.getId());
                claims.claim("username", user.getUsername());
                claims.claim("resources", user.getResources());
            }
        }
    }
}
