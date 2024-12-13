package com.strr.system.service;

import com.strr.system.model.SysRole;
import com.strr.base.service.ICrudService;

import java.util.List;

public interface ISysRoleService extends ICrudService<SysRole, Long> {
    /**
     * 获取角色列表
     */
    List<SysRole> listByParam(SysRole param);

    /**
     * 更新角色权限
     */
    void updateRel(Long roleId, Long[] resourceIds);

    /**
     * 获取角色权限
     */
    List<Long> listResourceId(Long roleId);

    /**
     * 删除角色
     */
    void removeInfo(Long id);
}
