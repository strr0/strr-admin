package com.strr.system.mapper;

import com.strr.system.model.SysResource;
import com.strr.base.mapper.CrudMapper;

import java.util.List;

public interface SysResourceMapper extends CrudMapper<SysResource, Integer> {
    /**
     * 获取用户权限
     */
    List<SysResource> listByUserId(Integer userId);
}
