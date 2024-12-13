package com.strr.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleResourceMapper {
    /**
     * 添加角色权限
     */
    int batchSave(@Param("roleId") Long roleId, @Param("resourceIds") Long[] resourceIds);

    /**
     * 获取权限id
     */
    List<Long> listByRoleId(Long roleId);

    /**
     * 删除角色权限关联(角色id)
     */
    int removeByRoleId(Long roleId);

    /**
     * 删除角色权限关联(资源id)
     */
    int removeByResourceId(Long resourceId);
}
