package com.strr.system.service.impl;

import com.strr.system.mapper.SysRoleResourceMapper;
import com.strr.system.mapper.SysUserRoleMapper;
import com.strr.system.mapper.SysRoleMapper;
import com.strr.system.model.SysRole;
import com.strr.system.service.ISysRoleService;
import com.strr.base.service.impl.CrudServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl extends CrudServiceImpl<SysRole, Long> implements ISysRoleService {
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleResourceMapper sysRoleResourceMapper;

    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper, SysUserRoleMapper sysUserRoleMapper, SysRoleResourceMapper sysRoleResourceMapper) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysRoleResourceMapper = sysRoleResourceMapper;
    }

    @Override
    protected SysRoleMapper getMapper() {
        return sysRoleMapper;
    }

    /**
     * 获取角色列表
     */
    @Override
    public List<SysRole> listByParam(SysRole param) {
        return sysRoleMapper.listByParam(param);
    }

    /**
     * 更新角色权限
     */
    @Override
    public void updateRel(Long roleId, Long[] resourceIds) {
        sysRoleResourceMapper.removeByRoleId(roleId);
        sysRoleResourceMapper.batchSave(roleId, resourceIds);
    }

    /**
     * 获取角色权限
     */
    @Override
    public List<Long> listResourceId(Long roleId) {
        return sysRoleResourceMapper.listByRoleId(roleId);
    }

    /**
     * 删除角色
     */
    @Override
    public void removeInfo(Long id) {
        sysUserRoleMapper.removeByRoleId(id);
        sysRoleResourceMapper.removeByRoleId(id);
        sysRoleMapper.remove(id);
    }
}
