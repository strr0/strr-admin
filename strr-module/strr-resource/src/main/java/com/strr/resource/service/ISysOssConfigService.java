package com.strr.resource.service;

import com.strr.base.service.ICrudService;
import com.strr.resource.model.SysOssConfig;

import java.util.List;

public interface ISysOssConfigService extends ICrudService<SysOssConfig, Long> {
    /**
     * 获取所有配置
     */
    List<SysOssConfig> listAll();
}
