package com.strr.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * 用户
 */
public interface LoginUserService extends UserDetailsService {
    /**
     * 获取用户权限
     */
    List<String> listPermsByUserId(Long userId);
}
