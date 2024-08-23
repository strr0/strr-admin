package com.strr.system.service;

import com.strr.system.model.SysUser;
import com.strr.system.model.bo.SysUserBo;
import com.strr.base.service.CrudService;

import java.util.List;

public interface SysUserService extends CrudService<SysUser, Integer> {
    /**
     * 保存用户
     */
    void saveInfo(SysUserBo sysUser);

    /**
     * 获取用户角色
     */
    List<Integer> listRoleId(Integer userId);

    /**
     * 删除用户
     */
    void removeInfo(Integer id);
}
