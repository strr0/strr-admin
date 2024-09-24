package com.strr.system.dubbo;

import com.strr.system.api.RemoteOauthClientService;
import com.strr.system.api.model.OauthClient;
import com.strr.system.mapper.SysOauthClientMapper;
import com.strr.system.model.SysOauthClient;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * 客户端服务
 */
@Service
@DubboService
public class RemoteOauthClientServiceImpl implements RemoteOauthClientService {
    private final SysOauthClientMapper sysOauthClientMapper;

    public RemoteOauthClientServiceImpl(SysOauthClientMapper sysOauthClientMapper) {
        this.sysOauthClientMapper = sysOauthClientMapper;
    }

    @Override
    public OauthClient getClientInfo(String clientId) {
        SysOauthClient client = sysOauthClientMapper.getByClientId(clientId);
        if (client != null) {
            OauthClient oauthClient = new OauthClient();
            oauthClient.setId(client.getId());
            oauthClient.setClientId(client.getClientId());
            oauthClient.setClientSecret(client.getClientSecret());
            oauthClient.setGrantTypes(client.getGrantType().split(","));
            oauthClient.setScopes(client.getScope().split(","));
            oauthClient.setRedirectUris(client.getRedirectUri().split(","));
            oauthClient.setAccessTimeout(client.getAccessTimeout());
            oauthClient.setRefreshTimeout(client.getRefreshTimeout());
            return oauthClient;
        }
        return null;
    }
}
