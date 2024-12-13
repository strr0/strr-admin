package com.strr.system.service;

import com.strr.system.model.SysUser;
import com.strr.system.model.bo.SysUserBo;
import com.strr.base.service.ICrudService;

import java.util.List;

public interface ISysUserService extends ICrudService<SysUser, Long> {
    /**
     * 保存用户
     */
    void saveInfo(SysUserBo sysUser);

    /**
     * 获取用户角色
     */
    List<Long> listRoleId(Long userId);

    /**
     * 删除用户
     */
    void removeInfo(Long id);
}
