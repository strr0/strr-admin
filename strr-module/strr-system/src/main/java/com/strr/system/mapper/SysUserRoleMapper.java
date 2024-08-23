package com.strr.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper {
    /**
     * 添加用户角色
     */
    int batchSave(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);

    /**
     * 获取角色id
     */
    List<Integer> listByUserId(Integer userId);

    /**
     * 删除用户角色关联
     */
    int removeByUserId(Integer userId);

    /**
     * 删除用户角色关联
     */
    int removeByRoleId(Integer roleId);
}
