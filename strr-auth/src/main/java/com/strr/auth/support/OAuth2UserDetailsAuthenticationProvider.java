package com.strr.auth.support;

import com.strr.auth.constant.AuthConstant;
import com.strr.auth.service.LoginUserService;
import com.strr.base.model.LoginBody;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.dao.AbstractOAuth2UserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class OAuth2UserDetailsAuthenticationProvider extends AbstractOAuth2UserDetailsAuthenticationProvider {
    private final LoginUserService loginUserService;
    private final PasswordEncoder passwordEncoder;

    public OAuth2UserDetailsAuthenticationProvider(LoginUserService loginUserService, PasswordEncoder passwordEncoder) {
        this.loginUserService = loginUserService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 加载用户信息
     */
    @Override
    protected UserDetails retrieveUser(String principal, LoginBody loginBody) throws AuthenticationException {
        String loginType = loginBody.getLoginType();
        UserDetails loadedUser = null;
        // 默认登录方式
        if (loginType == null || AuthConstant.LOGIN_TYPE_DEFAULT.equals(loginType)) {
            loadedUser = loginUserService.loadUserByUsername(principal);
        }
        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }

    /**
     * 授权校验
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, LoginBody loginBody) throws AuthenticationException {
        String loginType = loginBody.getLoginType();
        // 默认登录方式
        if (loginType == null || AuthConstant.LOGIN_TYPE_DEFAULT.equals(loginType)) {
            if (loginBody.getPrincipal() == null) {
                this.logger.debug("Failed to authenticate since no credentials provided");
                throw new BadCredentialsException(this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
            String presentedPassword = loginBody.getCredentials();
            if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
                this.logger.debug("Failed to authenticate since password does not match stored value");
                throw new BadCredentialsException(this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }
}
