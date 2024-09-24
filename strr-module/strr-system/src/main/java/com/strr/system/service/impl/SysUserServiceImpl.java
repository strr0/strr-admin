package com.strr.system.service.impl;

import com.strr.system.mapper.SysUserRoleMapper;
import com.strr.system.mapper.SysUserMapper;
import com.strr.system.model.SysUser;
import com.strr.system.model.bo.SysUserBo;
import com.strr.system.service.ISysUserService;
import com.strr.base.service.impl.CrudServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl extends CrudServiceImpl<SysUser, Integer> implements ISysUserService {
    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    public SysUserServiceImpl(SysUserMapper sysUserMapper, SysUserRoleMapper sysUserRoleMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    @Override
    protected SysUserMapper getMapper() {
        return sysUserMapper;
    }

    /**
     * 保存用户
     */
    @Override
    public void saveInfo(SysUserBo sysUser) {
        if (sysUser.getId() == null) {
            sysUser.setPassword("$2a$10$LBfxhQw8tw6a1eENVgk5l.mcmcM5LqAB4XIUF5BlNESO50Nq/WQ5S");  // abc123
            sysUserMapper.save(sysUser);
        } else {
            sysUserRoleMapper.removeByUserId(sysUser.getId());
            sysUserMapper.update(sysUser);
        }
        sysUserRoleMapper.batchSave(sysUser.getId(), sysUser.getRoleIds());
    }

    /**
     * 获取用户角色
     */
    @Override
    public List<Integer> listRoleId(Integer userId) {
        return sysUserRoleMapper.listByUserId(userId);
    }

    /**
     * 删除用户
     */
    @Override
    public void removeInfo(Integer id) {
        sysUserRoleMapper.removeByUserId(id);
        sysUserMapper.remove(id);
    }
}
