package com.strr.security.config.permission;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;

import com.strr.constant.Constant;
import com.strr.security.annotation.CheckPermission;
import com.strr.security.util.LoginUtil;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.aop.support.AopUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

/**
 * An {@link AuthorizationManager} which can determine if an {@link Authentication} may
 * invoke the {@link MethodInvocation} by evaluating an expression from the
 * {@link CheckPermission} annotation.
 */
public final class CheckPermissionAuthorizationManager implements AuthorizationManager<MethodInvocation> {
    /**
     * Determine if an {@link Authentication} has access to a method by evaluating an
     * expression from the {@link CheckPermission} annotation that the
     * {@link MethodInvocation} specifies.
     * @param authentication the {@link Supplier} of the {@link Authentication} to check
     * @param mi the {@link MethodInvocation} to check
     * @return an {@link AuthorizationDecision} or {@code null} if the
     * {@link CheckPermission} annotation is not present
     */
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation mi) {
        String[] permissions = this.getPermissions(mi);
        if (permissions == null || permissions.length == 0) {
            return null;
        }
        boolean granted = this.hasPermission(authentication, permissions);
        return new AuthorizationDecision(granted);
    }

    private String[] getPermissions(MethodInvocation mi) {
        Method method = mi.getMethod();
        Object target = mi.getThis();
        Class<?> targetClass = (target != null) ? target.getClass() : null;
        return getPermissions(method, targetClass);
    }

    private String[] getPermissions(Method method, Class<?> targetClass) {
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        CheckPermission checkPermission = specificMethod.getAnnotation(CheckPermission.class);
        if (checkPermission == null) {
            return null;
        }
        return checkPermission.value();
    }

    private boolean hasPermission(Supplier<Authentication> authentication, String[] requiredPermissions) {
        return LoginUtil.getClaims(authentication).map(map -> (List<String>) map.get(Constant.PERMS))
                .map(permissions -> {
                    if (permissions.isEmpty()) {
                        return false;
                    }
                    for (String requiredPermission : requiredPermissions) {
                        if (!permissions.contains(requiredPermission)) {
                            return false;
                        }
                    }
                    return true;
                }).orElse(false);
    }
}
