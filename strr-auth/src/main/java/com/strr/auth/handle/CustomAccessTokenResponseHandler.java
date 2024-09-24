package com.strr.auth.handle;

import com.strr.auth.model.LoginToken;
import com.strr.auth.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * 登录成功处理器
 */
public class CustomAccessTokenResponseHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();

        LoginToken loginToken = new LoginToken();
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            loginToken.setAccessToken(accessToken.getTokenValue());
        }
        if (refreshToken != null) {
            loginToken.setRefreshToken(refreshToken.getTokenValue());
        }
        ResponseUtil.writeResult(response, loginToken);
    }
}
