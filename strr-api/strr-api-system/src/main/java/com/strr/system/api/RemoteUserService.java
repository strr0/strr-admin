package com.strr.system.api;

import com.strr.system.api.model.LoginUser;

import java.util.List;

/**
 * 用户服务
 */
public interface RemoteUserService {
    /**
     * 获取用户信息
     */
    LoginUser getUserInfo(String username);

    /**
     * 获取用户权限
     */
    List<String> listPerms(Long userId);
}
