package com.strr.resource.service.impl;

import com.strr.base.service.impl.CrudServiceImpl;
import com.strr.base.constant.Constant;
import com.strr.resource.mapper.SysOssConfigMapper;
import com.strr.resource.model.SysOssConfig;
import com.strr.resource.service.ISysOssConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysOssConfigServiceImpl extends CrudServiceImpl<SysOssConfig, Long> implements ISysOssConfigService {
    private final SysOssConfigMapper sysOssConfigMapper;

    public SysOssConfigServiceImpl(SysOssConfigMapper sysOssConfigMapper) {
        this.sysOssConfigMapper = sysOssConfigMapper;
    }

    @Override
    protected SysOssConfigMapper getMapper() {
        return sysOssConfigMapper;
    }

    /**
     * 获取所有配置
     */
    @Override
    public List<SysOssConfig> listAll() {
        SysOssConfig param = new SysOssConfig();
        param.setStatus(Constant.YES);
        return sysOssConfigMapper.listByParam(param);
    }
}
