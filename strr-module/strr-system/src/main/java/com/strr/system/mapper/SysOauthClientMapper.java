package com.strr.system.mapper;

import com.strr.base.mapper.CrudMapper;
import com.strr.system.model.SysOauthClient;

public interface SysOauthClientMapper extends CrudMapper<SysOauthClient, Integer> {
    /**
     * 获取客户端配置
     */
    SysOauthClient getByClientId(String clientId);
}
