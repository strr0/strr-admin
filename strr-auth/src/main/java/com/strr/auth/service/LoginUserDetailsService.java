package com.strr.auth.service;

import com.strr.auth.model.LoginUserDetails;
import com.strr.system.api.RemoteUserService;
import com.strr.system.api.model.LoginUser;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户
 */
@Service
public class LoginUserDetailsService implements UserDetailsService {
    @DubboReference
    private RemoteUserService remoteUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = remoteUserService.getUserInfo(username);
        if (user != null) {
            LoginUserDetails loginUser = new LoginUserDetails();
            loginUser.setId(user.getId());
            loginUser.setUsername(user.getUsername());
            loginUser.setPassword(user.getPassword());
            loginUser.setNickname(user.getNickname());
            loginUser.setResources(user.getResources());
            return loginUser;
        }
        throw new UsernameNotFoundException(String.format("User %s not found.", username));
    }
}
