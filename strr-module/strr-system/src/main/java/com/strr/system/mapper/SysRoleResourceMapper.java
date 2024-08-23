package com.strr.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleResourceMapper {
    /**
     * 添加角色权限
     */
    int batchSave(@Param("roleId") Integer roleId, @Param("resourceIds") Integer[] resourceIds);

    /**
     * 获取权限id
     */
    List<Integer> listByRoleId(Integer roleId);

    /**
     * 删除角色权限关联(角色id)
     */
    int removeByRoleId(Integer roleId);

    /**
     * 删除角色权限关联(资源id)
     */
    int removeByResourceId(Integer resourceId);
}
