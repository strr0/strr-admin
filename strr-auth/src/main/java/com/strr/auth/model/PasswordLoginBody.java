package com.strr.auth.model;

import com.strr.auth.constant.AuthConstant;
import com.strr.base.model.LoginBody;

public class PasswordLoginBody implements LoginBody {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getLoginType() {
        return AuthConstant.LOGIN_TYPE_DEFAULT;
    }

    @Override
    public String getPrincipal() {
        return username;
    }

    @Override
    public String getCredentials() {
        return password;
    }
}
