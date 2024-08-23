package com.strr.system.model.bo;

import com.strr.system.model.SysUser;

public class SysUserBo extends SysUser {
    private Integer[] roleIds;

    public Integer[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Integer[] roleIds) {
        this.roleIds = roleIds;
    }
}
