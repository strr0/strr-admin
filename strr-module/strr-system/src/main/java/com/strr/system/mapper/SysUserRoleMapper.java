package com.strr.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper {
    /**
     * 添加用户角色
     */
    int batchSave(@Param("userId") Long userId, @Param("roleIds") Long[] roleIds);

    /**
     * 获取角色id
     */
    List<Long> listByUserId(Long userId);

    /**
     * 删除用户角色关联
     */
    int removeByUserId(Long userId);

    /**
     * 删除用户角色关联
     */
    int removeByRoleId(Long roleId);
}
