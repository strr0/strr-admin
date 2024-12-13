package com.strr.system.service;

import com.strr.system.model.SysResource;
import com.strr.base.service.ICrudService;

import java.util.List;

public interface ISysResourceService extends ICrudService<SysResource, Long> {
    /**
     * 权限列表
     */
    List<SysResource> listByParam(SysResource param);

    /**
     * 获取用户权限
     */
    List<SysResource> listByUserId(Long userId);

    /**
     * 删除权限
     */
    void removeInfo(Long id);
}
