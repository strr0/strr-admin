package com.strr.system.model.bo;

import com.strr.system.model.SysUser;

public class SysUserBo extends SysUser {
    private Long[] roleIds;

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
}
