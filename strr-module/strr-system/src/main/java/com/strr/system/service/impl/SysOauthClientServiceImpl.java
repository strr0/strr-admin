package com.strr.system.service.impl;

import com.strr.base.service.impl.CrudServiceImpl;
import com.strr.system.mapper.SysOauthClientMapper;
import com.strr.system.model.SysOauthClient;
import com.strr.system.service.ISysOauthClientService;
import org.springframework.stereotype.Service;

@Service
public class SysOauthClientServiceImpl extends CrudServiceImpl<SysOauthClient, Long> implements ISysOauthClientService {
    private final SysOauthClientMapper sysOauthClientMapper;

    public SysOauthClientServiceImpl(SysOauthClientMapper sysOauthClientMapper) {
        this.sysOauthClientMapper = sysOauthClientMapper;
    }

    @Override
    protected SysOauthClientMapper getMapper() {
        return sysOauthClientMapper;
    }
}
