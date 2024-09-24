package com.strr.system.api;

import com.strr.system.api.model.OauthClient;

/**
 * 客户端服务
 */
public interface RemoteOauthClientService {
    /**
     * 获取客户端配置
     */
    OauthClient getClientInfo(String clientId);
}
