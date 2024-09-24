package com.strr.system.api.model;

import java.io.Serial;
import java.io.Serializable;

public class OauthClient implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 客户端 id
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 授权方式
     */
    private String[] grantTypes;

    /**
     * 作用域
     */
    private String[] scopes;

    /**
     * 回调地址
     */
    private String[] redirectUris;

    /**
     * accessTimeout
     */
    private Long accessTimeout;

    /**
     * refreshTimeout
     */
    private Long refreshTimeout;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String[] getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(String[] grantTypes) {
        this.grantTypes = grantTypes;
    }

    public String[] getScopes() {
        return scopes;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }

    public String[] getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String[] redirectUris) {
        this.redirectUris = redirectUris;
    }

    public Long getAccessTimeout() {
        return accessTimeout;
    }

    public void setAccessTimeout(Long accessTimeout) {
        this.accessTimeout = accessTimeout;
    }

    public Long getRefreshTimeout() {
        return refreshTimeout;
    }

    public void setRefreshTimeout(Long refreshTimeout) {
        this.refreshTimeout = refreshTimeout;
    }
}
