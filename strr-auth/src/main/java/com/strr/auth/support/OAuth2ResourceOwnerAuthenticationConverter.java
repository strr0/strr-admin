package com.strr.auth.support;

import com.strr.auth.constant.AuthConstant;
import com.strr.auth.model.PasswordLoginBody;
import com.strr.base.model.LoginBody;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.web.authentication.AbstractOAuth2ResourceOwnerAuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.function.Consumer;

public class OAuth2ResourceOwnerAuthenticationConverter extends AbstractOAuth2ResourceOwnerAuthenticationConverter {
    @Override
    protected LoginBody getLoginBody(MultiValueMap<String, String> parameters, Consumer<String> throwError) {
        String loginType = parameters.getFirst(AuthConstant.LOGIN_TYPE_KEY);
        // 默认登录方式
        if (loginType == null || AuthConstant.LOGIN_TYPE_DEFAULT.equals(loginType)) {
            // username (REQUIRED)
            String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);
            if (!StringUtils.hasText(username) || parameters.get(OAuth2ParameterNames.USERNAME).size() != 1) {
                throwError.accept(OAuth2ParameterNames.USERNAME);
            }
            // password (REQUIRED)
            String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);
            if (!StringUtils.hasText(password) || parameters.get(OAuth2ParameterNames.PASSWORD).size() != 1) {
                throwError.accept(OAuth2ParameterNames.PASSWORD);
            }
            PasswordLoginBody loginBody = new PasswordLoginBody();
            loginBody.setUsername(username);
            loginBody.setPassword(password);
            return loginBody;
        }
        throw new OAuth2AuthenticationException("unsupported login type: " + loginType);
    }
}
