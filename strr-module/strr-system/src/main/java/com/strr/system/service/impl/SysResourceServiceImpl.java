package com.strr.system.service.impl;

import com.strr.base.constant.Constant;
import com.strr.system.mapper.SysRoleResourceMapper;
import com.strr.system.mapper.SysResourceMapper;
import com.strr.system.model.SysResource;
import com.strr.system.service.ISysResourceService;
import com.strr.base.service.impl.CrudServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysResourceServiceImpl extends CrudServiceImpl<SysResource, Long> implements ISysResourceService {
    private final SysResourceMapper sysResourceMapper;
    private final SysRoleResourceMapper sysRoleResourceMapper;

    public SysResourceServiceImpl(SysResourceMapper sysResourceMapper, SysRoleResourceMapper sysRoleResourceMapper) {
        this.sysResourceMapper = sysResourceMapper;
        this.sysRoleResourceMapper = sysRoleResourceMapper;
    }

    @Override
    protected SysResourceMapper getMapper() {
        return sysResourceMapper;
    }

    /**
     * 资源列表
     */
    @Override
    public List<SysResource> listByParam(SysResource param) {
        return sysResourceMapper.listByParam(param);
    }

    /**
     * 获取用户资源
     */
    @Override
    public List<SysResource> listByUserId(Long userId) {
        return sysResourceMapper.listByUserId(userId, Constant.YES);
    }

    /**
     * 删除资源
     */
    @Override
    public void removeInfo(Long id) {
        sysRoleResourceMapper.removeByResourceId(id);
        sysResourceMapper.remove(id);
    }
}
