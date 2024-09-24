package com.strr.system.model;

import com.strr.base.annotation.Id;
import com.strr.base.annotation.Table;
import com.strr.base.model.BaseModel;

/**
 * 资源
 */
@Table("sys_oauth_client")
public class SysOauthClient extends BaseModel {
    /**
     * 主键
     */
    @Id
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
    private String grantType;

    /**
     * 作用域
     */
    private String scope;

    /**
     * 回调地址
     */
    private String redirectUri;

    /**
     * accessTimeout
     */
    private Long accessTimeout;

    /**
     * refreshTimeout
     */
    private Long refreshTimeout;

    /**
     * 状态
     */
    private String status;

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

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
