package com.strr.system.dubbo;

import com.strr.constant.Constant;
import com.strr.system.api.RemoteUserService;
import com.strr.system.api.model.LoginUser;
import com.strr.system.mapper.SysResourceMapper;
import com.strr.system.mapper.SysUserMapper;
import com.strr.system.model.SysResource;
import com.strr.system.model.SysUser;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 * 用户服务
 */
@Service
@DubboService
public class RemoteUserServiceImpl implements RemoteUserService {
    private final SysUserMapper sysUserMapper;
    private final SysResourceMapper sysResourceMapper;

    public RemoteUserServiceImpl(SysUserMapper sysUserMapper, SysResourceMapper sysResourceMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysResourceMapper = sysResourceMapper;
    }

    /**
     * 获取用户信息
     */
    @Override
    public LoginUser getUserInfo(String username) {
        SysUser user = sysUserMapper.getByUsername(username);
        if (user != null) {
            LoginUser loginUser = new LoginUser();
            loginUser.setId(user.getId());
            loginUser.setUsername(user.getUsername());
            loginUser.setPassword(user.getPassword());
            loginUser.setNickname(user.getNickname());
            loginUser.setResources(sysResourceMapper.listByUserId(user.getId(), Constant.NO)
                    .stream().map(SysResource::getPerms).filter(StringUtils::hasLength).distinct()
                    .collect(Collectors.toList()));
            return loginUser;
        }
        return null;
    }
}
