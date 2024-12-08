package com.strr.system.controller;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.system.model.SysUser;
import com.strr.system.model.bo.SysUserBo;
import com.strr.system.service.ISysUserService;
import com.strr.base.controller.CrudController;
import com.strr.base.model.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${module.system:}/user")
public class SysUserController extends CrudController<SysUser, Integer> {
    private final ISysUserService sysUserService;

    public SysUserController(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    protected ISysUserService getService() {
        return sysUserService;
    }

    /**
     * 查询用户列表
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:user:list')")
    @GetMapping("/page")
    public Result<Page<SysUser>> page(SysUser param, Pageable pageable) {
        return super.page(param, pageable);
    }

    /**
     * 保存用户信息
     */
    @PreAuthorize("@pms.hasPermission('system:user:save')")
    @PostMapping
    public Result<Void> saveInfo(@RequestBody SysUserBo sysUser) {
        sysUserService.saveInfo(sysUser);
        return Result.ok();
    }

    /**
     * 获取用户角色
     */
    @PreAuthorize("@pms.hasPermission('system:user:query')")
    @GetMapping("/listRoleId")
    public Result<List<Integer>> listRoleId(Integer userId) {
        List<Integer> data = sysUserService.listRoleId(userId);
        return Result.ok(data);
    }

    /**
     * 删除用户
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:user:remove')")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Integer id) {
        sysUserService.removeInfo(id);
        return Result.ok();
    }

    /**
     * 查询用户详情
     */
    @Override
    @PreAuthorize("@pms.hasPermission('system:user:query')")
    @GetMapping("/{id}")
    public Result<SysUser> get(@PathVariable Integer id) {
        return super.get(id);
    }

    /**
     * 获取当前登录用户
     */
    @GetMapping("/getUserInfo")
    public Result<Map<String, Object>> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        return Result.ok(jwt.getClaims());
    }
}
