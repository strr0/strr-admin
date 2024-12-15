package org.springframework.security.oauth2.server.authorization.authentication;

import com.strr.base.model.LoginBody;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;

import java.util.Collections;

public class OAuth2UserDetailsAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringAuthorizationServerVersion.SERIAL_VERSION_UID;

    private final LoginBody loginBody;

    public OAuth2UserDetailsAuthenticationToken(LoginBody loginBody) {
        super(Collections.emptyList());
        this.loginBody = loginBody;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public LoginBody getLoginBody() {
        return loginBody;
    }
}
