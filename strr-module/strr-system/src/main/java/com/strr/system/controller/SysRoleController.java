package com.strr.system.controller;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.security.annotation.CheckPermission;
import com.strr.system.model.SysRole;
import com.strr.system.service.ISysRoleService;
import com.strr.base.controller.CrudController;
import com.strr.base.model.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${module.system:}/role")
public class SysRoleController extends CrudController<SysRole, Integer> {
    private final ISysRoleService sysRoleService;

    public SysRoleController(ISysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Override
    protected ISysRoleService getService() {
        return sysRoleService;
    }

    /**
     * 查询角色列表
     */
    @CheckPermission("system:role:list")
    @GetMapping("/page")
    public Result<Page<SysRole>> page(SysRole param, Pageable pageable) {
        return super.page(param, pageable);
    }

    /**
     * 获取角色列表
     */
    @CheckPermission("system:role:list")
    @GetMapping("/list")
    public Result<List<SysRole>> list(SysRole param) {
        List<SysRole> list = sysRoleService.listByParam(param);
        return Result.ok(list);
    }

    /**
     * 保存角色
     */
    @Override
    @CheckPermission("system:role:save")
    @PostMapping
    public Result<SysRole> save(@RequestBody SysRole sysRole) {
        return super.save(sysRole);
    }

    /**
     * 修改角色
     */
    @Override
    @CheckPermission("system:role:update")
    @PutMapping
    public Result<SysRole> update(@RequestBody SysRole sysRole) {
        return super.update(sysRole);
    }

    /**
     * 更新角色资源
     */
    @CheckPermission("system:role:update")
    @PostMapping("/updateRel")
    public Result<Void> updateRel(Integer roleId, Integer[] resourceIds) {
        sysRoleService.updateRel(roleId, resourceIds);
        return Result.ok();
    }

    /**
     * 获取角色资源
     */
    @CheckPermission("system:role:query")
    @GetMapping("/listResourceId")
    public Result<List<Integer>> listResourceId(Integer roleId) {
        List<Integer> data = sysRoleService.listResourceId(roleId);
        return Result.ok(data);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @Override
    @CheckPermission("system:role:remove")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Integer id) {
        sysRoleService.removeInfo(id);
        return Result.ok();
    }

    /**
     * 获取角色详情
     */
    @Override
    @CheckPermission("system:role:query")
    @GetMapping("/{id}")
    public Result<SysRole> get(Integer id) {
        return super.get(id);
    }
}
