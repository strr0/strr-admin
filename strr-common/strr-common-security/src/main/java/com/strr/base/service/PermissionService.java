package com.strr.base.service;

import com.strr.base.util.LoginUtil;
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
        return LoginUtil.getClaimsOptional().map(map -> (List<String>) map.get("resources"))
                .map(resources -> resources.contains(permission)).orElse(false);
    }
}
