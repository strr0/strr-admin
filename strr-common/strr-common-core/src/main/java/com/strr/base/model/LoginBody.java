package com.strr.base.model;

/**
 * 登录
 */
public interface LoginBody {
    String getLoginType();
    String getPrincipal();
    String getCredentials();
}
