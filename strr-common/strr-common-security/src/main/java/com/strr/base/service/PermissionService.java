package com.strr.base.service;

import com.strr.base.util.LoginUtil;
import com.strr.constant.Constant;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 权限校验
 */
public class PermissionService {
    public boolean hasPermission(String permission) {
        if (!StringUtils.hasLength(permission)) {
            return false;
        }
        return LoginUtil.getClaimsOptional().map(map -> (List<String>) map.get(Constant.PERMS))
                .map(resources -> resources.contains(permission)).orElse(false);
    }
}
