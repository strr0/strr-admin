package com.strr.system.mapper;

import com.strr.system.model.SysUser;
import com.strr.base.mapper.CrudMapper;

public interface SysUserMapper extends CrudMapper<SysUser, Integer> {
    /**
     * 获取用户信息
     */
    SysUser getByUsername(String username);
}
