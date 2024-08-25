package com.strr.system.controller;

import com.strr.system.model.SysRole;
import com.strr.system.service.SysRoleService;
import com.strr.base.controller.CrudController;
import com.strr.base.model.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class SysRoleController extends CrudController<SysRole, Integer> {
    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Override
    protected SysRoleService getService() {
        return sysRoleService;
    }

    /**
     * 获取角色列表
     */
    @GetMapping("/list")
    public Result<List<SysRole>> list(SysRole param) {
        List<SysRole> list = sysRoleService.listByParam(param);
        return Result.ok(list);
    }

    /**
     * 更新角色权限
     */
    @PostMapping("/updateRel")
    public Result<Void> updateRel(Integer roleId, Integer[] resourceIds) {
        sysRoleService.updateRel(roleId, resourceIds);
        return Result.ok();
    }

    /**
     * 获取角色权限
     */
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
    @DeleteMapping("/removeInfo")
    public Result<Void> removeInfo(Integer id) {
        sysRoleService.removeInfo(id);
        return Result.ok();
    }
}
